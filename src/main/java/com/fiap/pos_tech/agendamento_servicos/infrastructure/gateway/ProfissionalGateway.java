package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProfissionalGateway implements IProfissionalGateway {
    @Override
    public Profissional criarProfissional(Profissional profissional) {
        return null;
    }

    @Override
    public Profissional buscarProfissional(UUID idProfissional) {
        return null;
    }
}
