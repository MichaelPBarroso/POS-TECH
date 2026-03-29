package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto;

public record InputEndereco(String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
}
