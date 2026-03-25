package com.fiap.pos_tech.agendamento_servicos.application.usecase.notificarAgendamentoUseCase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.notificarAgendamentoUseCase.dto.InputNotificarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.notificarAgendamentoUseCase.dto.OutputNotificarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class NotificarAgendamentoUseCase {

    private final IAgendamentoGateway agendamentoGateway;

    private NotificarAgendamentoUseCase(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    public static NotificarAgendamentoUseCase create(IAgendamentoGateway gateway) {
        return new NotificarAgendamentoUseCase(gateway);
    }

    public OutputNotificarAgendamento execute(InputNotificarAgendamento input) {
        Agendamento agendamento = input.toEntity();

        agendamentoGateway.enviarNotificacaoAgendamento(agendamento);

        return new OutputNotificarAgendamento();
    }
}
