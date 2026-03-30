package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record OutputReagendarAgendamento(UUID id, LocalDateTime horario, UUID idServico, UUID idEstabelecimento, UUID idProfissional, UUID idCliente,  StatusAgendamentoEnum statusAgendamento) {

}
