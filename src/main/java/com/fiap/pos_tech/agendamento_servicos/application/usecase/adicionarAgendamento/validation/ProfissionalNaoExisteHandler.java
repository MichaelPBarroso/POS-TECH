package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.ProfissionalNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

public class ProfissionalNaoExisteHandler implements IAdicionarAgendamentoValidation {

    private final IProfissionalGateway profissionalGateway;

    public ProfissionalNaoExisteHandler(IProfissionalGateway profissionalGateway) {
        this.profissionalGateway = profissionalGateway;
    }

    @Override
    public void validate(Agendamento agendamento) {

        if (agendamento.getProfissional() == null) {
            throw new ProfissionalNaoExisteException();
        }

        Profissional profissional = profissionalGateway.buscarProfissional(agendamento.getProfissional().getId());

        if (profissional == null) {
            throw new ProfissionalNaoExisteException();
        }
    }
}
