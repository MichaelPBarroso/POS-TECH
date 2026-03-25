package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.util.List;

public class ReagendarAgendamentoValidationChain {

    private final List<IReagendarAgendamentoValidation> validations;

    public ReagendarAgendamentoValidationChain(List<IReagendarAgendamentoValidation> validations) {
        this.validations = validations;
    }

    public void validate(Agendamento agendamento) {
        validations.forEach(validation -> validation.validate(agendamento));
    }
}
