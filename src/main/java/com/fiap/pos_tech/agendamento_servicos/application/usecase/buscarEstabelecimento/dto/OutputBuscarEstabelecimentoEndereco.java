package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import java.time.LocalTime;
import java.util.UUID;

public record OutputBuscarEstabelecimentoEndereco(UUID id, String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
}
