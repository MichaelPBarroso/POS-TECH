package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ServicoOferecidoEntity;

public class ServicoOferecidoPresenter {

    public static ServicoOferecido toDomain(ServicoOferecidoEntity servicoOferecidoEntity){
        return ServicoOferecido.create(
                servicoOferecidoEntity.getId(),
                servicoOferecidoEntity.getNome(),
                servicoOferecidoEntity.getValor()
        );
    }

    public static ServicoOferecidoEntity toEntity(ServicoOferecido servicoOferecido){
        return ServicoOferecidoEntity.builder()
                .id(servicoOferecido.getId())
                .nome(servicoOferecido.getNome())
                .valor(servicoOferecido.getValor())
                .build();
    }
}
