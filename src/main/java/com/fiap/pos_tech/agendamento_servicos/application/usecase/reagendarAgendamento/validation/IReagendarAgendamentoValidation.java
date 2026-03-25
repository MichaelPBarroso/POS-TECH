package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public interface IReagendarAgendamentoValidation {

    void validate(Agendamento agendamento);
}
