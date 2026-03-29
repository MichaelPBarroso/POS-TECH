package com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AgendamentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation.IReagendarAgendamentoValidation;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class AgendamentoNaoExisteHandler implements IReagendarAgendamentoValidation {

    private final IAgendamentoGateway agendamentoGateway;

    public AgendamentoNaoExisteHandler(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    @Override
    public void validate(Agendamento agendamento) {

        if(agendamento == null)
            throw new AgendamentoNaoExisteException();

        Agendamento agendamentoDatabase = agendamentoGateway.buscarAgendamento(agendamento.getId());

        if (agendamentoDatabase == null) {
            throw new AgendamentoNaoExisteException();
        }
    }
}
