package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public interface IAdicionarAgendamentoValidation {

    void validate(Agendamento agendamento);
}
