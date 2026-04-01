package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto.InputReagendarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto.OutputReagendarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation.ReagendarAgendamentoValidationChain;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReagendarAgendamenoUseCaseTest {

    @Test
    @DisplayName("Deve reagendar agendamento com sucesso")
    void deveReagendarAgendamentoComSucesso() {
        UUID agendamentoId = UUID.randomUUID();
        Agendamento agendamento = criarAgendamento(agendamentoId);
        LocalDate novaData = LocalDate.of(2026, 4, 6);
        LocalTime novoHorario = LocalTime.of(16, 0);

        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        ReagendarAgendamentoValidationChain validationChain = mock(ReagendarAgendamentoValidationChain.class);

        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(agendamento);
        when(agendamentoGateway.atualizarAgendamento(any(Agendamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReagendarAgendamenoUseCase useCase = ReagendarAgendamenoUseCase.create(agendamentoGateway, validationChain);

        OutputReagendarAgendamento output = useCase.execute(new InputReagendarAgendamento(agendamentoId, novaData, novoHorario));

        assertNotNull(output);
        assertEquals(agendamentoId, output.id());
        assertEquals(novaData, output.data());
        assertEquals(novoHorario, output.horario());
        assertEquals(agendamento.getServicoOferecido().getId(), output.idServico());
        assertEquals(agendamento.getEstabelecimento().getId(), output.idEstabelecimento());
        assertEquals(agendamento.getProfissional().getId(), output.idProfissional());
        assertEquals(agendamento.getCliente().getId(), output.idCliente());

        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(validationChain).validate(agendamento);
        verify(agendamentoGateway).atualizarAgendamento(agendamento);
    }

    @Test
    @DisplayName("Nao deve reagendar agendamento quando validacao falhar")
    void naoDeveReagendarAgendamentoQuandoValidacaoFalhar() {
        UUID agendamentoId = UUID.randomUUID();
        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        Agendamento agendamento = criarAgendamento(agendamentoId);
        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(agendamento);

        ReagendarAgendamentoValidationChain validationChain = mock(ReagendarAgendamentoValidationChain.class);
        doThrow(new IllegalArgumentException("Horario indisponivel")).when(validationChain).validate(agendamento);

        ReagendarAgendamenoUseCase useCase = ReagendarAgendamenoUseCase.create(agendamentoGateway, validationChain);

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(new InputReagendarAgendamento(agendamentoId, LocalDate.of(2026, 4, 6), LocalTime.of(16, 0)))
        );

        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(validationChain).validate(agendamento);
        verify(agendamentoGateway, never()).atualizarAgendamento(any());
    }

    private Agendamento criarAgendamento(UUID agendamentoId) {
        UUID estabelecimentoId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Endereco endereco = Endereco.create(UUID.randomUUID(), "Rua D", "40", null, "Centro", "Sao Paulo", "SP", "04000-000");
        Estabelecimento estabelecimento = Estabelecimento.create(estabelecimentoId, "Studio Reagendar", LocalTime.of(8, 0), LocalTime.of(18, 0), endereco, null);
        Profissional profissional = Profissional.create(profissionalId, "Joana", List.of(), List.of(), estabelecimento, List.of());
        ServicoOferecido servico = ServicoOferecido.create(servicoId, "Coloracao", 120.0, profissional);
        Cliente cliente = Cliente.create(clienteId, "Cliente", "12345678900", "cliente@email.com");

        return Agendamento.create(
                agendamentoId,
                LocalDate.of(2026, 4, 4),
                LocalTime.of(9, 0),
                servico,
                estabelecimento,
                profissional,
                StatusAgendamentoEnum.AGENDADO,
                cliente
        );
    }
}
