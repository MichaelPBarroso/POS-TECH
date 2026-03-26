package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAvaliacaoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoGateway implements IAvaliacaoGateway {

    @Override
    public Avaliacao criarAvaliacao(Avaliacao avaliacao) {
        return null;
    }
}
