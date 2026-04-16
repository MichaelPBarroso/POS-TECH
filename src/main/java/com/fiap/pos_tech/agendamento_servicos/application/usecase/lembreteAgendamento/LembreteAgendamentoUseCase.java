package com.fiap.pos_tech.agendamento_servicos.application.usecase.lembreteAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;

public class LembreteAgendamentoUseCase {

    private final IAgendamentoGateway agendamentoGateway;

    private LembreteAgendamentoUseCase(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    public static LembreteAgendamentoUseCase create(IAgendamentoGateway agendamentoGateway) {
        return new LembreteAgendamentoUseCase(agendamentoGateway);
    }

    public void execute(){
        agendamentoGateway.lembreteAgendamentoDoProximoDia();
    }
}
