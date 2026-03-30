package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Especialidade;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EspecialidadeEntity;

public class EspecialidadePresenter {

    public static Especialidade toDomain(EspecialidadeEntity especialidadeEntity) {
        return Especialidade.create(
                especialidadeEntity.getId(),
                especialidadeEntity.getNome()
        );
    }

    public static EspecialidadeEntity toEntity(Especialidade especialidade) {
        return EspecialidadeEntity.builder()
                .id(especialidade.getId())
                .nome(especialidade.getNome())
                .build();
    }
}
