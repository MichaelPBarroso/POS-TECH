package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AvaliacaoNotaInvalidaException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAvaliacaoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.InputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.OutputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation.AdicionarAvaliacaoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation.NotaInvalidaHandler;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Cliente;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdicionarAvaliacaoUseCaseTest {

    @Test
    @DisplayName("Deve adicionar avaliacao com sucesso")
    void deveAdicionarAvaliacaoComSucesso() {
        UUID agendamentoId = UUID.randomUUID();
        UUID avaliacaoId = UUID.randomUUID();
        LocalDateTime dataAvaliacao = LocalDateTime.of(2026, 4, 2, 15, 0);
        Agendamento agendamento = criarAgendamento(agendamentoId);
        Avaliacao avaliacaoPersistida = Avaliacao.create(avaliacaoId, agendamento, 5, "Excelente atendimento", dataAvaliacao);

        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        IAvaliacaoGateway avaliacaoGateway = mock(IAvaliacaoGateway.class);
        AdicionarAvaliacaoValidationChain validationChain = mock(AdicionarAvaliacaoValidationChain.class);

        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(agendamento);
        when(avaliacaoGateway.criarAvaliacao(any())).thenReturn(avaliacaoPersistida);

        AdicionarAvaliacaoUseCase useCase = AdicionarAvaliacaoUseCase.create(avaliacaoGateway, validationChain, agendamentoGateway);

        OutputAdicionarAvaliacao output = useCase.execute(new InputAdicionarAvaliacao(5, agendamentoId, "Excelente atendimento", dataAvaliacao));

        assertNotNull(output);
        assertEquals(avaliacaoId, output.id());
        assertEquals(5, output.nota());
        assertEquals(agendamentoId, output.idAgendamento());
        assertEquals("Excelente atendimento", output.comentario());
        assertEquals(dataAvaliacao, output.dataAvaliacao());

        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(validationChain).validate(any(Avaliacao.class));
        verify(avaliacaoGateway).criarAvaliacao(any(Avaliacao.class));
        verify(avaliacaoGateway).atualizarMediaNotasAvaliacao(agendamento.getEstabelecimento().getId());
    }

    @Test
    @DisplayName("Nao deve adicionar avaliacao com nota invalida")
    void naoDeveAdicionarAvaliacaoComNotaInvalida() {
        UUID agendamentoId = UUID.randomUUID();
        Agendamento agendamento = criarAgendamento(agendamentoId);

        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        IAvaliacaoGateway avaliacaoGateway = mock(IAvaliacaoGateway.class);
        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(agendamento);

        AdicionarAvaliacaoValidationChain validationChain = new AdicionarAvaliacaoValidationChain(List.of(new NotaInvalidaHandler()));

        AdicionarAvaliacaoUseCase useCase = AdicionarAvaliacaoUseCase.create(avaliacaoGateway, validationChain, agendamentoGateway);

        assertThrows(
                AvaliacaoNotaInvalidaException.class,
                () -> useCase.execute(new InputAdicionarAvaliacao(6, agendamentoId, "Comentario", LocalDateTime.now()))
        );

        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(avaliacaoGateway, never()).criarAvaliacao(any());
        verify(avaliacaoGateway, never()).atualizarMediaNotasAvaliacao(any());
    }

    private Agendamento criarAgendamento(UUID agendamentoId) {
        UUID estabelecimentoId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Endereco endereco = Endereco.create(UUID.randomUUID(), "Rua B", "20", null, "Centro", "Sao Paulo", "SP", "02000-000");
        Estabelecimento estabelecimento = Estabelecimento.create(estabelecimentoId, "Studio", LocalTime.of(8, 0), LocalTime.of(18, 0), endereco, null);
        Profissional profissional = Profissional.create(profissionalId, "Ana", List.of(), List.of(), estabelecimento, List.of());
        ServicoOferecido servico = ServicoOferecido.create(servicoId, "Escova", 70.0, profissional);
        Cliente cliente = Cliente.create(clienteId, "Cliente", "12345678900", "cliente@email.com");

        return Agendamento.create(
                agendamentoId,
                LocalDate.of(2026, 4, 2),
                LocalTime.of(14, 0),
                servico,
                estabelecimento,
                profissional,
                StatusAgendamentoEnum.AGENDADO,
                cliente
        );
    }
}
