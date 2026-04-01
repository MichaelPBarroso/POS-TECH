package com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AgendamentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.dto.InputCancelarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.dto.OutputCancelarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation.AgendamentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation.CancelarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Cliente;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CancelarAgendamentoUseCaseTest {

    @Test
    @DisplayName("Deve cancelar agendamento com sucesso")
    void deveCancelarAgendamentoComSucesso() {
        UUID agendamentoId = UUID.randomUUID();
        Agendamento agendamento = criarAgendamento(agendamentoId);

        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        CancelarAgendamentoValidationChain validationChain = mock(CancelarAgendamentoValidationChain.class);

        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(agendamento);

        CancelarAgendamentoUseCase useCase = CancelarAgendamentoUseCase.create(agendamentoGateway, validationChain);

        OutputCancelarAgendamento output = useCase.execute(new InputCancelarAgendamento(agendamentoId));

        assertNotNull(output);
        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(validationChain).validate(agendamento);
        verify(agendamentoGateway).cancelarAgendamento(agendamentoId);
    }

    @Test
    @DisplayName("Nao deve cancelar agendamento inexistente")
    void naoDeveCancelarAgendamentoInexistente() {
        UUID agendamentoId = UUID.randomUUID();

        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(null);

        CancelarAgendamentoValidationChain validationChain = new CancelarAgendamentoValidationChain(
                List.of(new AgendamentoNaoExisteHandler(agendamentoGateway))
        );

        CancelarAgendamentoUseCase useCase = CancelarAgendamentoUseCase.create(agendamentoGateway, validationChain);

        assertThrows(AgendamentoNaoExisteException.class, () -> useCase.execute(new InputCancelarAgendamento(agendamentoId)));

        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(agendamentoGateway, never()).cancelarAgendamento(agendamentoId);
    }

    private Agendamento criarAgendamento(UUID agendamentoId) {
        UUID estabelecimentoId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Endereco endereco = Endereco.create(UUID.randomUUID(), "Rua A", "10", null, "Centro", "Sao Paulo", "SP", "01000-000");
        Estabelecimento estabelecimento = Estabelecimento.create(
                estabelecimentoId,
                "Studio",
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                endereco,
                null
        );
        Profissional profissional = Profissional.create(profissionalId, "Maria", List.of(), List.of(), estabelecimento, List.of());
        ServicoOferecido servico = ServicoOferecido.create(servicoId, "Corte", 50.0, profissional);
        Cliente cliente = Cliente.create(clienteId, "Cliente", "12345678900", "cliente@email.com");

        return Agendamento.create(
                agendamentoId,
                LocalDate.of(2026, 4, 2),
                LocalTime.of(10, 0),
                servico,
                estabelecimento,
                profissional,
                StatusAgendamentoEnum.AGENDADO,
                cliente
        );
    }
}
