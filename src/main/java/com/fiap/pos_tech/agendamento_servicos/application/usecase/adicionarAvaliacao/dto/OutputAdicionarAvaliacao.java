package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OutputAdicionarAvaliacao(UUID id, Integer nota, UUID idAgendamento, String comentario, LocalDateTime dataAvaliacao) {
}
