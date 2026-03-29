package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto;

import java.math.BigDecimal;

public record InputServicoOferecido(String nome, BigDecimal valor) {
}
