package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;

import java.util.List;

public class AdicionarFotoValidationChain {

    private List<IAdicionarFotoValidation> validations;

    public AdicionarFotoValidationChain(List<IAdicionarFotoValidation> validations) {
        this.validations = validations;
    }

    public void validate(FotoEstabelecimento fotoEstabelecimento) {
        validations.forEach(validation -> validation.validate(fotoEstabelecimento));
    }
}
