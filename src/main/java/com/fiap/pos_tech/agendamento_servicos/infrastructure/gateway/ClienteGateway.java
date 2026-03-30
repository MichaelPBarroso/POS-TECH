package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IClienteGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Cliente;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ClienteEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.ClienteJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.ClientePresenter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ClienteGateway implements IClienteGateway {

    private final ClienteJPARepository clienteJPARepository;

    @Override
    public Cliente buscarCliente(UUID idCliente) {
        Optional<ClienteEntity> entityOptional = clienteJPARepository.findById(idCliente);

        return entityOptional.map(ClientePresenter::toDomain).orElse(null);
    }
}
