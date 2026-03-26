package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import org.springframework.stereotype.Component;

@Component
public class ProfissionalGateway implements IProfissionalGateway {
    @Override
    public Profissional criarProfissional(Profissional profissional) {
        return null;
    }
}
