package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IClienteGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClienteGateway implements IClienteGateway {

    @Override
    public Cliente buscarCliente(UUID idCliente) {
        return null;
    }
}
