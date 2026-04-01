package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record InputAdicionarAgendamento(LocalDate data, LocalTime horario, UUID idServico, UUID idEstabelecimento, UUID idProfissional, UUID idCliente) {

}
