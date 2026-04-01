package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.dto;

import java.util.UUID;

public record OutputAdicionarFoto(UUID id, String urlFoto, UUID idEstabelecimento) {
}
