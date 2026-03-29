package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OutputReagendarAgendamento(UUID id, LocalDateTime horario, UUID idServico, UUID idEstabelecimento, UUID idProfissional, UUID idCliente) {

}
