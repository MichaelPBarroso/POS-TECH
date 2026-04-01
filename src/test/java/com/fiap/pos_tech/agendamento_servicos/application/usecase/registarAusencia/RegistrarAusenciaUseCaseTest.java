package com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AgendamentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.dto.InputRegistarAusencia;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.dto.OutputRegistrarAusencia;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.validation.RegistrarAusenciaValidationChain;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrarAusenciaUseCaseTest {

    @Test
    @DisplayName("Deve registrar ausencia com sucesso")
    void deveRegistrarAusenciaComSucesso() {
        UUID agendamentoId = UUID.randomUUID();
        Agendamento agendamento = criarAgendamento(agendamentoId);

        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        RegistrarAusenciaValidationChain validationChain = mock(RegistrarAusenciaValidationChain.class);

        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(agendamento);

        RegistrarAusenciaUseCase useCase = RegistrarAusenciaUseCase.create(agendamentoGateway, validationChain);

        OutputRegistrarAusencia output = useCase.execute(new InputRegistarAusencia(agendamentoId.toString()));

        assertNotNull(output);
        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(validationChain).validate(agendamento);
        verify(agendamentoGateway).registrarAusenciaAgendamento(agendamentoId);
    }

    @Test
    @DisplayName("Nao deve registrar ausencia quando validacao falhar")
    void naoDeveRegistrarAusenciaQuandoValidacaoFalhar() {
        UUID agendamentoId = UUID.randomUUID();
        Agendamento agendamento = criarAgendamento(agendamentoId);

        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        RegistrarAusenciaValidationChain validationChain = mock(RegistrarAusenciaValidationChain.class);

        when(agendamentoGateway.buscarAgendamento(agendamentoId)).thenReturn(agendamento);
        doThrow(new AgendamentoNaoExisteException()).when(validationChain).validate(agendamento);

        RegistrarAusenciaUseCase useCase = RegistrarAusenciaUseCase.create(agendamentoGateway, validationChain);

        assertThrows(AgendamentoNaoExisteException.class, () -> useCase.execute(new InputRegistarAusencia(agendamentoId.toString())));

        verify(agendamentoGateway).buscarAgendamento(agendamentoId);
        verify(validationChain).validate(agendamento);
        verify(agendamentoGateway, never()).registrarAusenciaAgendamento(agendamentoId);
    }

    private Agendamento criarAgendamento(UUID agendamentoId) {
        UUID estabelecimentoId = UUID.randomUUID();
        UUID profissionalId = UUID.randomUUID();
        UUID servicoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Endereco endereco = Endereco.create(UUID.randomUUID(), "Rua E", "50", null, "Centro", "Sao Paulo", "SP", "05000-000");
        Estabelecimento estabelecimento = Estabelecimento.create(estabelecimentoId, "Studio Ausencia", LocalTime.of(8, 0), LocalTime.of(18, 0), endereco, null);
        Profissional profissional = Profissional.create(profissionalId, "Pedro", List.of(), List.of(), estabelecimento, List.of());
        ServicoOferecido servico = ServicoOferecido.create(servicoId, "Manicure", 45.0, profissional);
        Cliente cliente = Cliente.create(clienteId, "Cliente", "12345678900", "cliente@email.com");

        return Agendamento.create(
                agendamentoId,
                LocalDate.of(2026, 4, 4),
                LocalTime.of(11, 0),
                servico,
                estabelecimento,
                profissional,
                StatusAgendamentoEnum.AGENDADO,
                cliente
        );
    }
}
