package com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public record InputCancelarAgendamento() {

    public Agendamento toEntity(){
        return new Agendamento();
    }
}
