package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.EstabelecimentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;

public class EstabelecimentoNaoExisteHandler implements IAdicionarFotoValidation {

    private final IEstabelecimentoGateway estabelecimentoGateway;

    public EstabelecimentoNaoExisteHandler(IEstabelecimentoGateway estabelecimentoGateway) {
        this.estabelecimentoGateway = estabelecimentoGateway;
    }

    @Override
    public void validate(FotoEstabelecimento fotoEstabelecimento) {

        if (fotoEstabelecimento.getEstabelecimento() == null) {
            throw new EstabelecimentoNaoExisteException();
        }

        Estabelecimento estabelecimento = estabelecimentoGateway.buscarEstabelecimento(fotoEstabelecimento.getEstabelecimento().getId());

        if (estabelecimento == null) {
            throw new EstabelecimentoNaoExisteException();
        }
    }
}
