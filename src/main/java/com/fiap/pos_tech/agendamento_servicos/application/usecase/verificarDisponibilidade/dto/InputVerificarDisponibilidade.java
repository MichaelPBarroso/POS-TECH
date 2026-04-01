package com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto;

import java.time.LocalDate;
import java.util.UUID;

public record InputVerificarDisponibilidade(UUID profissionalId, LocalDate data) {
}
