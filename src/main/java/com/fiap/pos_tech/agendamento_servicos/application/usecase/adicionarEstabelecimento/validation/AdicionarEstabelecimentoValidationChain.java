package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

import java.util.List;

public class AdicionarEstabelecimentoValidationChain {

    private final List<IAdicionarEstabelecimentoValidation> validations;

    public AdicionarEstabelecimentoValidationChain(List<IAdicionarEstabelecimentoValidation> validations) {
        this.validations = validations;
    }

    public void validate(Estabelecimento estabelecimento){
        for (IAdicionarEstabelecimentoValidation validation : validations) {
            validation.validate(estabelecimento);
        }
    }
}
