package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto;

public record EnderecoDTO(String id, String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
}
