package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto;

import java.time.LocalTime;
import java.util.UUID;

public record OutputHorarioDisponivel(UUID id, LocalTime horario) {
}
