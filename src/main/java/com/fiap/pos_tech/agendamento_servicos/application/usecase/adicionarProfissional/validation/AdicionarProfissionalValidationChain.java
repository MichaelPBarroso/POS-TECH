package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

import java.util.List;

public class AdicionarProfissionalValidationChain {

    private final List<IAdicionarProfissionalValidation> validations;

    public AdicionarProfissionalValidationChain(List<IAdicionarProfissionalValidation> validations) {
        this.validations = validations;
    }

    public void validate(Profissional profissional) {
        for (IAdicionarProfissionalValidation validation : validations) {
            validation.validate(profissional);
        }
    }

}
