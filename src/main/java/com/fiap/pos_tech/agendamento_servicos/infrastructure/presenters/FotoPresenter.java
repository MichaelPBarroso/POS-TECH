package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.FotoEstabelecimentoEntity;

public class FotoPresenter {

    public static FotoEstabelecimento toDomain(FotoEstabelecimentoEntity fotoEstabelecimentoEntity) {
        return FotoEstabelecimento.create(
                fotoEstabelecimentoEntity.getId(),
                EstabelecimentoPresenter.toDomain(fotoEstabelecimentoEntity.getEstabelecimento()),
                fotoEstabelecimentoEntity.getUrl()
        );
    }

    public static FotoEstabelecimentoEntity toEntity(FotoEstabelecimento fotoEstabelecimento) {
        return FotoEstabelecimentoEntity.builder()
                .id(fotoEstabelecimento.getId())
                .url(fotoEstabelecimento.getUrl())
                .estabelecimento(EstabelecimentoPresenter.toEntity(fotoEstabelecimento.getEstabelecimento()))
                .build();
    }
}
