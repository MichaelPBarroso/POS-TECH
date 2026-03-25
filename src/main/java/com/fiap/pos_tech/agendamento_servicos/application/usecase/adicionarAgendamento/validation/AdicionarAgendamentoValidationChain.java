package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.util.List;

public class AdicionarAgendamentoValidationChain {

    private final List<IAdicionarAgendamentoValidation> validations;

    public AdicionarAgendamentoValidationChain(List<IAdicionarAgendamentoValidation> validations) {
        this.validations = validations;
    }

    public void validate(Agendamento agendamento) {
        for (IAdicionarAgendamentoValidation validation : validations) {
            validation.validate(agendamento);
        }
    }
}
