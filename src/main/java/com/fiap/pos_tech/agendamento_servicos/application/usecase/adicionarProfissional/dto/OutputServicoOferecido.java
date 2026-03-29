package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OutputServicoOferecido(UUID id, String nome, BigDecimal valor) {
}
