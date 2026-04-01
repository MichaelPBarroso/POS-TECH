package com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto;

import com.fiap.pos_tech.agendamento_servicos.domain.model.HorarioDisponivel;

import java.util.List;

public record OutputVerificarDisponibilidade(List<HorarioDisponivel> horariosDisponiveis) {
}
