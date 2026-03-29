package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Cliente;

import java.util.UUID;

public interface IClienteGateway {

    Cliente buscarCliente(UUID idCliente);
}
