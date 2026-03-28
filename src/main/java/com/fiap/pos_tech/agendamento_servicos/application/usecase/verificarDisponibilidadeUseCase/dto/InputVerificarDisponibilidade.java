package com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidadeUseCase.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

import java.time.LocalDateTime;

public record InputVerificarDisponibilidade(Estabelecimento estabelecimento, Profissional profissional, LocalDateTime horario) {
}
