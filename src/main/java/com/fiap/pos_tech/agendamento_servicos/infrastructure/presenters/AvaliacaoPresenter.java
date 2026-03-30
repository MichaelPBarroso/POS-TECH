package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AvaliacaoEntity;

public class AvaliacaoPresenter {

    public static Avaliacao toDomain(AvaliacaoEntity avaliacaoEntity) {
        return Avaliacao.create(
                avaliacaoEntity.getId(),
                AgendamentoPresenter.toDomain(avaliacaoEntity.getAgendamento()),
                avaliacaoEntity.getNota(),
                avaliacaoEntity.getComentario(),
                avaliacaoEntity.getDataAvaliacao()
        );
    }

    public static AvaliacaoEntity toEntity(Avaliacao avaliacao) {
        return AvaliacaoEntity.builder()
                .id(avaliacao.getId())
                .nota(avaliacao.getNota())
                .comentario(avaliacao.getComentario())
                .dataAvaliacao(avaliacao.getDataAvaliacao())
                .agendamento(AgendamentoPresenter.toEntity(avaliacao.getAgendamento()))
                .build();
    }
}
