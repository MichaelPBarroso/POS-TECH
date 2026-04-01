package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import java.time.LocalTime;
import java.util.UUID;

public record InputBuscarEstabelecimento(
        UUID id,
        String nome,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        InputBuscarEstabelecimentoEndereco endereco,
        Integer notaMaiorQue,
        Integer notaMenorQue,
        InputBuscarEstabelecimentoServico servico,
        LocalTime horarioAgendamento
) {

}
