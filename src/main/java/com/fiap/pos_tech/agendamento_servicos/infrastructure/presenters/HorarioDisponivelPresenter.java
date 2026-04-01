package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.HorarioDisponivel;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.HorarioDisponivelEntity;

public class HorarioDisponivelPresenter {

    public static HorarioDisponivel toDomain(HorarioDisponivelEntity horarioDisponivelEntity) {
        return HorarioDisponivel.create(horarioDisponivelEntity.getId(), horarioDisponivelEntity.getHorario());
    }

    public static HorarioDisponivel toDomainProfissional(HorarioDisponivelEntity horarioDisponivelEntity) {
        return HorarioDisponivel.create(horarioDisponivelEntity.getId(), horarioDisponivelEntity.getHorario(), ProfissionalPresenter.toDomain(horarioDisponivelEntity.getProfissional()));
    }

    public static HorarioDisponivelEntity toEntity(HorarioDisponivel horarioDisponivel) {
        return HorarioDisponivelEntity.builder()
                .id(horarioDisponivel.getId())
                .horario(horarioDisponivel.getHorario())
                .build();
    }
}
