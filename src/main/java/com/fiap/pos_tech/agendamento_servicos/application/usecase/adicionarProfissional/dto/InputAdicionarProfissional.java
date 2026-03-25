package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

import java.util.UUID;

public record InputAdicionarProfissional(UUID id, String nome) {

    public Profissional toEntity() {
        return new Profissional();
    }
}
