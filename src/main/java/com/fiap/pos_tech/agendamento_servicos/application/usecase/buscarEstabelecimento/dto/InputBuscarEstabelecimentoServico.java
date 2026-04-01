package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

public record InputBuscarEstabelecimentoServico(
        String nome,
        Double precoMaiorQue,
        Double precoMenorQue
) {
}
