package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto;

import java.time.LocalTime;

public record InputEstabelecimento(String nome, LocalTime horarioAbertura, LocalTime horarioFechamento, InputEndereco endereco) {

}
