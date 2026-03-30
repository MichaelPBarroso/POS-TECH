package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto;

public record EstabelecimentoDTO(String id, String nome, String horarioAbertura, String horarioFechamento, EnderecoDTO endereco) {
}
