package com.fiap.pos_tech.agendamento_servicos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AgendamentoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EstabelecimentoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.AgendamentoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.EstabelecimentoJPARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiIntegrationTest {

    private static final String CLIENTE_ID = "55555555-5555-5555-5555-555555555555";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AgendamentoJPARepository agendamentoJPARepository;

    @Autowired
    private EstabelecimentoJPARepository estabelecimentoJPARepository;

    @Test
    void deveCriarEstabelecimentoProfissionalEFotoViaApisRest() throws Exception {
        SetupIds setup = criarSetupBasico("rest-cadastro", "Avenida Rest Cadastro", "Corte Premium", "09:00:00", "10:00:00", "15:30:00");

        JsonNode estabelecimento = setup.estabelecimento();
        assertThat(estabelecimento.path("nome").asText()).isEqualTo("Studio rest-cadastro");
        assertThat(estabelecimento.path("horario_abertura").asText()).isEqualTo("08:00");
        assertThat(estabelecimento.path("endereco").path("logradouro").asText()).isEqualTo("Avenida Rest Cadastro");

        JsonNode foto = uploadFoto(setup.estabelecimentoId());
        assertThat(foto.path("id").asText()).isNotBlank();
        assertThat(foto.path("url").asText()).isEqualTo("{URL de exemplo}?filename=fachada.jpg");

        JsonNode profissional = setup.profissional();
        assertThat(profissional.path("nome").asText()).isEqualTo("Profissional rest-cadastro");
        assertThat(profissional.path("idEstabelecimento").asText()).isEqualTo(setup.estabelecimentoId().toString());
        assertThat(profissional.path("servico").size()).isEqualTo(1);
        assertThat(profissional.path("especialidade").size()).isEqualTo(2);
        assertThat(profissional.path("horario").size()).isEqualTo(3);
    }

    @Test
    void deveGerenciarAgendamentosViaApisRest() throws Exception {
        SetupIds setup = criarSetupBasico("rest-agendamento", "Rua Agenda", "Massagem Relaxante", "09:00:00", "10:00:00", "15:30:00");

        JsonNode agendamentoCriado = criarAgendamento(setup, "2026-04-10", "09:00:00");
        UUID agendamentoId = UUID.fromString(agendamentoCriado.path("id").asText());

        assertThat(agendamentoCriado.path("statusAgendamento").asText()).isEqualTo("AGENDADO");

        JsonNode reagendado = putJson("/agendamento/" + agendamentoId + "/reagendar", """
                {
                  "data": "2026-04-11",
                  "horario": "15:30:00"
                }
                """, 200);

        assertThat(reagendado.path("data").asText()).isEqualTo("2026-04-11");
        assertThat(reagendado.path("horario").asText()).isEqualTo("15:30");

        String cancelamento = patchSemBody("/agendamento/" + agendamentoId + "/cancelar");
        assertThat(cancelamento).isEqualTo("Cancelamento realizado com sucesso.");

        AgendamentoEntity agendamentoCancelado = agendamentoJPARepository.findById(agendamentoId).orElseThrow();
        assertThat(agendamentoCancelado.getStatusAgendamentoEnum()).isEqualTo(StatusAgendamentoEnum.CANCELADO);

        JsonNode segundoAgendamento = criarAgendamento(setup, "2026-04-12", "10:00:00");
        UUID segundoAgendamentoId = UUID.fromString(segundoAgendamento.path("id").asText());

        String ausencia = patchSemBody("/agendamento/" + segundoAgendamentoId + "/ausente");
        assertThat(ausencia).isEqualTo("Registro de ausencia realizado com sucesso.");

        AgendamentoEntity agendamentoAusente = agendamentoJPARepository.findById(segundoAgendamentoId).orElseThrow();
        assertThat(agendamentoAusente.getStatusAgendamentoEnum()).isEqualTo(StatusAgendamentoEnum.AUSENTE);
    }

    @Test
    void deveRegistrarAvaliacaoEAtualizarNotaDoEstabelecimento() throws Exception {
        SetupIds setup = criarSetupBasico("rest-avaliacao", "Rua Avaliacao", "Limpeza de Pele", "11:00:00", "12:00:00");
        JsonNode agendamento = criarAgendamento(setup, "2026-04-13", "11:00:00");

        JsonNode avaliacao = postJson("/avaliacao", """
                {
                  "nota": 4,
                  "idAgendamento": "%s",
                  "comentario": "Atendimento preciso e pontual."
                }
                """.formatted(agendamento.path("id").asText()), 201);

        assertThat(avaliacao.path("id").asText()).isNotBlank();
        assertThat(avaliacao.path("nota").asInt()).isEqualTo(4);
        assertThat(avaliacao.path("idAgendamento").asText()).isEqualTo(agendamento.path("id").asText());
        assertThat(avaliacao.path("comentario").asText()).isEqualTo("Atendimento preciso e pontual.");
        assertThat(avaliacao.path("data_avaliacao").asText()).isNotBlank();

        EstabelecimentoEntity estabelecimento = estabelecimentoJPARepository.findById(setup.estabelecimentoId()).orElseThrow();
        assertThat(estabelecimento.getMediaNotas()).isNotNull();
        assertThat(estabelecimento.getMediaNotas().compareTo(BigDecimal.valueOf(4L))).isZero();
    }

    @Test
    void deveConsultarApisGraphQlComDadosPersistidos() throws Exception {
        SetupIds setup = criarSetupBasico("graphql", "Avenida GraphQL", "Design de Sobrancelha", "09:00:00", "10:00:00", "14:00:00");
        uploadFoto(setup.estabelecimentoId());
        criarAgendamento(setup, "2026-04-14", "09:00:00");

        JsonNode buscaResponse = postGraphQl("""
                query {
                  buscarEstabelecimentos(
                    estabelecimentoDTO: {
                      endereco: { logradouro: "Avenida GraphQL" }
                      servico: { nome: "Design de Sobrancelha", precoMaiorQue: 40.0, precoMenorQue: 100.0 }
                    }
                  ) {
                    id
                    nome
                    endereco { logradouro }
                    profissionais {
                      id
                      nome
                      servicosOferecidos { nome valor }
                    }
                    fotos { url }
                  }
                }
                """);

        JsonNode estabelecimentos = buscaResponse.path("data").path("buscarEstabelecimentos");
        assertThat(estabelecimentos.size()).isGreaterThanOrEqualTo(1);
        JsonNode estabelecimento = estabelecimentos.get(0);
        assertThat(estabelecimento.path("nome").asText()).isEqualTo("Studio graphql");
        assertThat(estabelecimento.path("endereco").path("logradouro").asText()).isEqualTo("Avenida GraphQL");
        assertThat(estabelecimento.path("profissionais").get(0).path("nome").asText()).isEqualTo("Profissional graphql");
        assertThat(estabelecimento.path("profissionais").get(0).path("servicosOferecidos").get(0).path("nome").asText())
                .isEqualTo("Design de Sobrancelha");
        assertThat(estabelecimento.path("fotos").get(0).path("url").asText()).isEqualTo("{URL de exemplo}?filename=fachada.jpg");

        JsonNode disponibilidadeResponse = postGraphQl("""
                query {
                  disponibilidadeProfissional(
                    profissionalId: "%s",
                    data: "2026-04-14"
                  ) {
                    horario
                    profissionalId
                  }
                }
                """.formatted(setup.profissionalId()));

        JsonNode disponibilidade = disponibilidadeResponse.path("data").path("disponibilidadeProfissional");
        List<String> horariosDisponiveis = StreamSupport.stream(disponibilidade.spliterator(), false)
                .map(node -> node.path("horario").asText())
                .toList();

        assertThat(horariosDisponiveis)
                .contains("10:00", "14:00")
                .doesNotContain("09:00");
    }

    private SetupIds criarSetupBasico(String suffix, String logradouro, String servicoNome, String... horarios) throws Exception {
        JsonNode estabelecimento = postJson("/estabelecimento", """
                {
                  "nome": "Studio %s",
                  "horario_abertura": "08:00:00",
                  "horario_fechamento": "18:00:00",
                  "endereco": {
                    "logradouro": "%s",
                    "numero": "100",
                    "complemento": "Sala 1",
                    "bairro": "Centro",
                    "cidade": "Sao Paulo",
                    "estado": "SP",
                    "cep": "01310-100"
                  }
                }
                """.formatted(suffix, logradouro), 201);

        UUID estabelecimentoId = UUID.fromString(estabelecimento.path("id").asText());
        JsonNode profissional = postJson("/profissional", profissionalBody(suffix, servicoNome, estabelecimentoId, horarios), 201);

        return new SetupIds(
                estabelecimentoId,
                UUID.fromString(profissional.path("id").asText()),
                UUID.fromString(profissional.path("servico").get(0).path("id").asText()),
                estabelecimento,
                profissional
        );
    }

    private String profissionalBody(String suffix, String servicoNome, UUID estabelecimentoId, String... horarios) throws Exception {
        StringBuilder horariosJson = new StringBuilder();
        for (int i = 0; i < horarios.length; i++) {
            if (i > 0) {
                horariosJson.append(",");
            }
            horariosJson.append("""
                    {
                      "horario": "%s"
                    }
                    """.formatted(horarios[i]));
        }

        Map<String, Object> body = Map.of(
                "nome", "Profissional " + suffix,
                "servico", new Object[]{
                        Map.of("nome", servicoNome, "valor", 80.0)
                },
                "especialidade", new Object[]{
                        Map.of("nome", "Especialidade 1"),
                        Map.of("nome", "Especialidade 2")
                },
                "idEstabelecimento", estabelecimentoId.toString(),
                "horario", objectMapper.readTree("[" + horariosJson + "]")
        );

        return objectMapper.writeValueAsString(body);
    }

    private JsonNode uploadFoto(UUID estabelecimentoId) throws Exception {
        MockMultipartFile arquivo = new MockMultipartFile(
                "foto",
                "fachada.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "conteudo-foto".getBytes(StandardCharsets.UTF_8)
        );

        MvcResult result = mockMvc.perform(multipart("/estabelecimento/{idEstabelecimento}/foto", estabelecimentoId)
                        .file(arquivo))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    private JsonNode criarAgendamento(SetupIds setup, String data, String horario) throws Exception {
        return postJson("/agendamento", """
                {
                  "data": "%s",
                  "horario": "%s",
                  "idServico": "%s",
                  "idEstabelecimento": "%s",
                  "idProfissional": "%s",
                  "idCliente": "%s"
                }
                """.formatted(
                data,
                horario,
                setup.servicoId(),
                setup.estabelecimentoId(),
                setup.profissionalId(),
                CLIENTE_ID
        ), 201);
    }

    private JsonNode postGraphQl(String query) throws Exception {
        MvcResult result = mockMvc.perform(post("/graphql")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("query", query))))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
        assertThat(response.path("errors").isMissingNode() || response.path("errors").isEmpty()).isTrue();
        return response;
    }

    private JsonNode postJson(String url, String body, int expectedStatus) throws Exception {
        MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(expectedStatus))
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    private JsonNode putJson(String url, String body, int expectedStatus) throws Exception {
        MvcResult result = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(expectedStatus))
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    private String patchSemBody(String url) throws Exception {
        MvcResult result = mockMvc.perform(patch(url))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    private record SetupIds(
            UUID estabelecimentoId,
            UUID profissionalId,
            UUID servicoId,
            JsonNode estabelecimento,
            JsonNode profissional
    ) {
    }
}
