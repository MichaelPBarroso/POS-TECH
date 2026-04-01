package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import java.time.LocalTime;
import java.util.UUID;

public record OutputBuscarEstabelecimentoHorarioDisponivel(
        UUID id,
        LocalTime horario
) {
}
