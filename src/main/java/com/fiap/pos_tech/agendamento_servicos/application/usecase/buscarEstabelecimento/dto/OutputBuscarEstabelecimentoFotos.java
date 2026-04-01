package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import java.util.UUID;

public record OutputBuscarEstabelecimentoFotos(
        UUID id,
        String url
) {
}
