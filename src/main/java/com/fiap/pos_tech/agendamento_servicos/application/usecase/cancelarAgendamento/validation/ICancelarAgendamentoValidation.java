package com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public interface ICancelarAgendamentoValidation {

    void validate(Agendamento agendamento);

}
