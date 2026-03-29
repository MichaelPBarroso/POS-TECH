package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record InputAdicionarAvaliacao(Integer nota, UUID idAgendamento, String comentario, LocalDateTime dataAvaliacao) {

    public Avaliacao toEntity(){
        return new Avaliacao();
    }
}
