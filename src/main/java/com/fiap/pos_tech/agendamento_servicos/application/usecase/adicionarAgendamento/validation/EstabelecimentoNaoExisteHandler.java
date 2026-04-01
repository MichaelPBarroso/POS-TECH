package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.EstabelecimentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.exceptions.ProfissionalNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

public class EstabelecimentoNaoExisteHandler implements IAdicionarAgendamentoValidation {

    private final IEstabelecimentoGateway profissionalGateway;

    public EstabelecimentoNaoExisteHandler(IEstabelecimentoGateway profissionalGateway) {
        this.profissionalGateway = profissionalGateway;
    }

    @Override
    public void validate(Agendamento agendamento) {

        if (agendamento.getEstabelecimento() == null) {
            throw new EstabelecimentoNaoExisteException();
        }

        Estabelecimento estabelecimento = profissionalGateway.buscarEstabelecimento(agendamento.getEstabelecimento().getId());

        if (estabelecimento == null) {
            throw new EstabelecimentoNaoExisteException();
        }
    }
}
