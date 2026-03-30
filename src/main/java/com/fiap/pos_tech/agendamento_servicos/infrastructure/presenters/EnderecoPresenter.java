package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EnderecoEntity;

public class EnderecoPresenter {

    public static Endereco toDomain(EnderecoEntity enderecoEntity) {
        return Endereco.create(
                enderecoEntity.getId(),
                enderecoEntity.getLogradouro(),
                enderecoEntity.getNumero(),
                enderecoEntity.getComplemento(),
                enderecoEntity.getBairro(),
                enderecoEntity.getCidade(),
                enderecoEntity.getEstado(),
                enderecoEntity.getCep()
        );
    }

    public static EnderecoEntity toEntity(Endereco endereco) {
        return EnderecoEntity.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }
}
