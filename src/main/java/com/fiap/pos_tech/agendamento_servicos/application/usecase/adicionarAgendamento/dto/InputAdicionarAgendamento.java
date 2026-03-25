package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public record InputAdicionarAgendamento() {

    public Agendamento toEntity() {
        return new Agendamento();
    }
}
