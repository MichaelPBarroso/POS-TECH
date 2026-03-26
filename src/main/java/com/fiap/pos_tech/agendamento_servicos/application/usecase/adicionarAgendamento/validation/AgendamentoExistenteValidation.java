package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AgendamentoJaExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class AgendamentoExistenteValidation implements IAdicionarAgendamentoValidation {

    private final IAgendamentoGateway agendamentoGateway;

    public AgendamentoExistenteValidation(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    @Override
    public void validate(Agendamento agendamento) {

        Agendamento agendamentoDatabase = agendamentoGateway.buscarAgendamento(agendamento.getEstabelecimento(), agendamento.getHorario(), agendamento.getProfissional());

        if (agendamentoDatabase != null) {
            throw new AgendamentoJaExisteException(agendamentoDatabase.getHorario().toString());
        }
    }
}
