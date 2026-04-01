package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OutputBuscarEstabelecimentoServicoOferecido(
        UUID id,
        String nome,
        BigDecimal valor
) {
}
