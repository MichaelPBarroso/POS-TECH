package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Cliente;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ClienteEntity;

public class ClientePresenter {

    public static Cliente toDomain(ClienteEntity clienteEntity){
        return Cliente.create(
                clienteEntity.getId(),
                clienteEntity.getNome(),
                clienteEntity.getCpf(),
                clienteEntity.getEmail()
        );
    }

    public static ClienteEntity toEntity(Cliente cliente){
        return ClienteEntity.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .build();
    }
}
