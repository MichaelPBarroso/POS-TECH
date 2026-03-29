package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record InputAdicionarAgendamento(LocalDateTime horario, UUID idServico, UUID idEstabelecimento, UUID idProfissional, UUID idCliente) {

}
