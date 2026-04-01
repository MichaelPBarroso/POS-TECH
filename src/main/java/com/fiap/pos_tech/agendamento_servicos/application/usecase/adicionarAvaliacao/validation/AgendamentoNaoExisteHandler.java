package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AgendamentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

public class AgendamentoNaoExisteHandler implements IAdicionarAvaliacaoValidation{

    private final IAgendamentoGateway agendamentoGateway;

    public AgendamentoNaoExisteHandler(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    @Override
    public void validate(Avaliacao avaliacao) {

        if(avaliacao.getAgendamento() == null)
            throw new AgendamentoNaoExisteException();

        Agendamento agendamentoDatabase = agendamentoGateway.buscarAgendamento(avaliacao.getAgendamento().getId());

        if (agendamentoDatabase == null) {
            throw new AgendamentoNaoExisteException();
        }

    }
}
