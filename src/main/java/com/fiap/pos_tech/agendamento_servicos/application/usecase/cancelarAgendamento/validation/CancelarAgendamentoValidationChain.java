package com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.util.List;

public class CancelarAgendamentoValidationChain {

    private final List<ICancelarAgendamentoValidation> validations;

    public CancelarAgendamentoValidationChain(List<ICancelarAgendamentoValidation> validations) {
        this.validations = validations;
    }

    public void validate(Agendamento agendamento){
        for (ICancelarAgendamentoValidation validation : validations) {
            validation.validate(agendamento);
        }
    }
}
