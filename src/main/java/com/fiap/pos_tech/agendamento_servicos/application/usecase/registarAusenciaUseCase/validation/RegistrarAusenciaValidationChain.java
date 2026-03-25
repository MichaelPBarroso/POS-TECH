package com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusenciaUseCase.validation;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation.IReagendarAgendamentoValidation;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.util.List;

public class RegistrarAusenciaValidationChain {

    private final List<IRegistrarAusenciaValidation> validations;

    public RegistrarAusenciaValidationChain(List<IRegistrarAusenciaValidation> validations) {
        this.validations = validations;
    }

    public void  validate(Agendamento agendamento) {
        this.validations.forEach(validation -> validation.validate(agendamento));
    }
}
