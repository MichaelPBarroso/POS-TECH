package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.time.LocalDateTime;
import java.util.UUID;

public record InputReagendarAgendamento(UUID idAgendamento, LocalDateTime horarioAgendamento) {

}
