package com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public interface IRegistrarAusenciaValidation {

    void validate(Agendamento agendamento);
}
