package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.EstabelecimentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

public class EstabelecimentoNaoExisteHandler implements IAdicionarProfissionalValidation {

    private final IEstabelecimentoGateway profissionalGateway;

    public EstabelecimentoNaoExisteHandler(IEstabelecimentoGateway profissionalGateway) {
        this.profissionalGateway = profissionalGateway;
    }

    @Override
    public void validate(Profissional profissional) {

        if (profissional.getEstabelecimento() == null) {
            throw new EstabelecimentoNaoExisteException();
        }

        Estabelecimento estabelecimento = profissionalGateway.buscarEstabelecimento(profissional.getEstabelecimento().getId());

        if (estabelecimento == null) {
            throw new EstabelecimentoNaoExisteException();
        }
    }

}
