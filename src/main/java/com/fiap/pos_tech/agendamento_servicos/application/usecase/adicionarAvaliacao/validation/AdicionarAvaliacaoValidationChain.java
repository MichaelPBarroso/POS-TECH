package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

import java.util.List;

public class AdicionarAvaliacaoValidationChain {

    private final List<IAdicionarAvaliacaoValidation> validations;

    public AdicionarAvaliacaoValidationChain(List<IAdicionarAvaliacaoValidation> validations) {
        this.validations = validations;
    }

    public void validate(Avaliacao avaliacao){
        for (IAdicionarAvaliacaoValidation validation : validations) {
            validation.validate(avaliacao);
        }
    }
}
