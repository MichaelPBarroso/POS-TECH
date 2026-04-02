package com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.ProfissionalNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.InputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.OutputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.HorarioDisponivel;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VerificarDisponibilidadeUseCaseTest {

    @Test
    void deveRemoverHorariosJaAgendadosDaListaDeDisponiveis() {
        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        IProfissionalGateway profissionalGateway = mock(IProfissionalGateway.class);

        UUID profissionalId = UUID.randomUUID();
        LocalDate data = LocalDate.of(2026, 3, 31);

        HorarioDisponivel horario09 = HorarioDisponivel.create(LocalTime.of(9, 0));
        HorarioDisponivel horario10 = HorarioDisponivel.create(LocalTime.of(10, 0));
        HorarioDisponivel horario11 = HorarioDisponivel.create(LocalTime.of(11, 0));

        Estabelecimento estabelecimento = new Estabelecimento();
        Profissional profissional = Profissional.create(
                profissionalId,
                "Profissional",
                "profissional@email.com",
                List.of(),
                List.of(),
                estabelecimento,
                List.of(horario09, horario10, horario11)
        );

        Agendamento agendamento = new Agendamento();
        agendamento.setHorario(LocalTime.of(10, 0));

        when(profissionalGateway.buscarProfissionalECaracteriscas(profissionalId)).thenReturn(profissional);
        when(agendamentoGateway.buscarAgendamentos(estabelecimento, data, profissional)).thenReturn(List.of(agendamento));

        VerificarDisponibilidadeUseCase useCase = VerificarDisponibilidadeUseCase.create(agendamentoGateway, profissionalGateway);

        OutputVerificarDisponibilidade output = useCase.execute(new InputVerificarDisponibilidade(profissionalId, data));

        assertEquals(2, output.horariosDisponiveis().size());
        assertIterableEquals(List.of(horario09, horario11), output.horariosDisponiveis());
    }

    @Test
    void devePropagarErroQuandoProfissionalNaoExistir() {
        IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
        IProfissionalGateway profissionalGateway = mock(IProfissionalGateway.class);

        UUID profissionalId = UUID.randomUUID();
        LocalDate data = LocalDate.of(2026, 3, 31);

        when(profissionalGateway.buscarProfissionalECaracteriscas(profissionalId))
                .thenThrow(new ProfissionalNaoExisteException());

        VerificarDisponibilidadeUseCase useCase = VerificarDisponibilidadeUseCase.create(agendamentoGateway, profissionalGateway);

        ProfissionalNaoExisteException thrown = assertThrows(
                ProfissionalNaoExisteException.class,
                () -> useCase.execute(new InputVerificarDisponibilidade(profissionalId, data))
        );

        assertEquals("Profissional não encontrado", thrown.getMessage());
        verify(profissionalGateway).buscarProfissionalECaracteriscas(profissionalId);
        verify(agendamentoGateway, never()).buscarAgendamentos(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }
}
