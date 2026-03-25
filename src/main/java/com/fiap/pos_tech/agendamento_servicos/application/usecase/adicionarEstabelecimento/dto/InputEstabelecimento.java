package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

public record InputEstabelecimento() {

    public Estabelecimento toEntity() {
        return new Estabelecimento();
    }
}
