package com.fiap.pos_tech.agendamento_servicos.performance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class AgendamentoCargaSimulation extends Simulation {

    private static final String BASE_URL = System.getProperty("baseUrl", "http://localhost:8080");
    private static final String CLIENTE_ID_1 = "55555555-5555-5555-5555-555555555555";
    private static final String CLIENTE_ID_2 = "44444444-3333-4444-3333-444444444444";
    private static final String[] HORARIOS = {"09:00:00", "10:00:00", "15:30:00"};
    private static final int USERS_PER_SEC = Integer.getInteger("gatling.agendamento.usersPerSec", 5);
    private static final int DURATION_SECONDS = Integer.getInteger("gatling.agendamento.durationSeconds", 30);
    private static final int RAMP_SECONDS = Integer.getInteger("gatling.agendamento.rampSeconds", 10);
    private static final int REQUESTS_PER_USER = Integer.getInteger("gatling.agendamento.requestsPerUser", 3);
    private static final LocalDate START_DATE = LocalDate.of(2026, 5, 1);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static volatile String estabelecimentoId;
    private static volatile String profissionalId;
    private static volatile String servicoId;

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private final ScenarioBuilder agendamentoCarga = scenario("Carga agendamento")
            .repeat(REQUESTS_PER_USER, "slotIndex").on(
                    exec(session -> {
                        int slotIndex = session.getInt("slotIndex");
                        long baseOffset = session.userId() * REQUESTS_PER_USER + slotIndex;
                        String data = START_DATE.plusDays(baseOffset).toString();
                        String horario = HORARIOS[slotIndex % HORARIOS.length];
                        String clienteId = slotIndex % 2 == 0 ? CLIENTE_ID_1 : CLIENTE_ID_2;

                        return session
                                .set("dataAgendamento", data)
                                .set("horarioAgendamento", horario)
                                .set("clienteId", clienteId);
                    })
                            .exec(
                                    http("Criar agendamento")
                                            .post("/agendamento")
                                            .body(StringBody(session -> """
                                                    {
                                                      "data": "%s",
                                                      "horario": "%s",
                                                      "idServico": "%s",
                                                      "idEstabelecimento": "%s",
                                                      "idProfissional": "%s",
                                                      "idCliente": "%s"
                                                    }
                                                    """.formatted(
                                                    session.getString("dataAgendamento"),
                                                    session.getString("horarioAgendamento"),
                                                    servicoId,
                                                    estabelecimentoId,
                                                    profissionalId,
                                                    session.getString("clienteId")
                                            )))
                                            .check(status().is(201))
                                            .check(jsonPath("$.id").saveAs("agendamentoId"))
                            )
            );

    public AgendamentoCargaSimulation() {
        setUp(
                agendamentoCarga.injectOpen(
                        rampUsersPerSec(1).to(USERS_PER_SEC).during(Duration.ofSeconds(RAMP_SECONDS)),
                        constantUsersPerSec(USERS_PER_SEC).during(Duration.ofSeconds(DURATION_SECONDS))
                )
        ).protocols(httpProtocol).assertions(
                global().failedRequests().count().is(0L),
                global().responseTime().percentile4().lt(2000)
        );
    }

    @Override
    public void before() {
        try {
            criarSetupBase();
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Falha ao preparar dados base para o teste de carga.", e);
        }
    }

    private static void criarSetupBase() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String suffix = UUID.randomUUID().toString().substring(0, 8);

        JsonNode estabelecimento = postJson(client, "/estabelecimento", """
                {
                  "nome": "Studio gatling-%s",
                  "horario_abertura": "08:00:00",
                  "horario_fechamento": "18:00:00",
                  "endereco": {
                    "logradouro": "Rua Gatling %s",
                    "numero": "100",
                    "complemento": "Sala 1",
                    "bairro": "Centro",
                    "cidade": "Sao Paulo",
                    "estado": "SP",
                    "cep": "01310-100"
                  }
                }
                """.formatted(suffix, suffix), 201);

        estabelecimentoId = estabelecimento.path("id").asText();

        JsonNode profissional = postJson(client, "/profissional", """
                {
                  "nome": "Profissional gatling-%s",
                  "servico": [
                    {
                      "nome": "Corte Gatling",
                      "valor": 80.0
                    }
                  ],
                  "especialidade": [
                    {
                      "nome": "Especialidade 1"
                    },
                    {
                      "nome": "Especialidade 2"
                    }
                  ],
                  "idEstabelecimento": "%s",
                  "horario": [
                    {
                      "horario": "09:00:00"
                    },
                    {
                      "horario": "10:00:00"
                    },
                    {
                      "horario": "15:30:00"
                    }
                  ]
                }
                """.formatted(suffix, estabelecimentoId), 201);

        profissionalId = profissional.path("id").asText();
        servicoId = profissional.path("servico").get(0).path("id").asText();
    }

    private static JsonNode postJson(HttpClient client, String path, String body, int expectedStatus)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != expectedStatus) {
            throw new IllegalStateException("Resposta inesperada em " + path + ": " + response.statusCode() + " - " + response.body());
        }

        return OBJECT_MAPPER.readTree(response.body());
    }
}
