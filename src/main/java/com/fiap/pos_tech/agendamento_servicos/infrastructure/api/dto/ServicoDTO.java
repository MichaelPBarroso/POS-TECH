package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto;

public record ServicoDTO(
        String nome,
        Double precoMaiorQue,
        Double precoMenorQue
) {
}
