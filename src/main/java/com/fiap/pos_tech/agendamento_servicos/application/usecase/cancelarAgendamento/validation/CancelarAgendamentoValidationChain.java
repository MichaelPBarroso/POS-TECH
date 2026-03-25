package com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.IAdicionarAgendamentoValidation;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.util.List;

public class CancelarAgendamentoValidationChain {

    private final List<IAdicionarAgendamentoValidation> validations;

    public CancelarAgendamentoValidationChain(List<IAdicionarAgendamentoValidation> validations) {
        this.validations = validations;
    }

    public void validate(Agendamento agendamento){
        for (IAdicionarAgendamentoValidation validation : validations) {
            validation.validate(agendamento);
        }
    }
}
