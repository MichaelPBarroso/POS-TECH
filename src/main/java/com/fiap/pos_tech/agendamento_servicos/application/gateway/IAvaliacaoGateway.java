package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

public interface IAvaliacaoGateway {

    Avaliacao criarAvaliacao(Avaliacao avaliacao);
}
