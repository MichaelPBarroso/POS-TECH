package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AgendamentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class AgendamentoNãoExisteHandler implements IReagendarAgendamentoValidation {

    private final IAgendamentoGateway agendamentoGateway;

    public AgendamentoNãoExisteHandler(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    @Override
    public void validate(Agendamento agendamento) {

        Agendamento agendamentoDatabase = agendamentoGateway.buscarAgendamento(agendamento.getId());

        if (agendamentoDatabase == null) {
            throw new AgendamentoNaoExisteException();
        }
    }
}
