package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto;

import java.util.UUID;

public record OutputEndereco(UUID id, String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
}
