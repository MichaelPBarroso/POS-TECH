package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record InputReagendarAgendamento(UUID idAgendamento, LocalDate data, LocalTime horario) {

}
