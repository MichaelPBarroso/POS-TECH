package com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public record InputRegistarAusencia() {

    public Agendamento toEntity(){
        return new Agendamento();
    }
}
