package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record OutputAdicionarAgendamento(UUID id, LocalDate data, LocalTime horario, UUID idServico, UUID idEstabelecimento, UUID idProfissional, UUID idCliente, StatusAgendamentoEnum statusAgendamento) {
}
