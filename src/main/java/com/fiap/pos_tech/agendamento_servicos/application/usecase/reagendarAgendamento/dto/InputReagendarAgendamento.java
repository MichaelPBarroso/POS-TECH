package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public record InputReagendarAgendamento() {

    public Agendamento toEntity(){
        return new Agendamento();
    }

}
