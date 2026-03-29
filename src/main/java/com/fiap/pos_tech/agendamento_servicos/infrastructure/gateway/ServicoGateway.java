package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IServicoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ServicoGateway implements IServicoGateway {
    @Override
    public List<ServicoOferecido> buscarServicosOferecidos(Estabelecimento estabelecimento) {
        return List.of();
    }

    @Override
    public ServicoOferecido buscarServicoOferecido(UUID idServicoOferecido) {
        return null;
    }
}
