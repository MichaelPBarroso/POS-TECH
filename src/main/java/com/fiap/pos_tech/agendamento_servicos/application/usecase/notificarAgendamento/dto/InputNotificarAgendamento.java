package com.fiap.pos_tech.agendamento_servicos.application.usecase.notificarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public record InputNotificarAgendamento() {

    public Agendamento toEntity() {
        return new Agendamento();
    }
}
