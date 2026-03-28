package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

public record InputBuscarEstabelecimento() {

    public Estabelecimento toEntity(){
        return new Estabelecimento();
    }
}
