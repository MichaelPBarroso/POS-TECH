package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto;

import java.time.LocalTime;
import java.util.UUID;

public record OutputEstabelecimento(UUID id, String nome, LocalTime horarioAbertura, LocalTime horarioFechamento, OutputEndereco endereco) {
}
