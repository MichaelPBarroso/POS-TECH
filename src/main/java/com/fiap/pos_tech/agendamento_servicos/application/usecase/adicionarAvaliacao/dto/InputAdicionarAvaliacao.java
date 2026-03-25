package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

public record InputAdicionarAvaliacao() {

    public Avaliacao toEntity(){
        return new Avaliacao();
    }
}
