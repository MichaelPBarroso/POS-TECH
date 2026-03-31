package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HorarioDisponivel {

    private UUID id;
    private LocalTime horario;
    private Profissional profissional;

    public static HorarioDisponivel create(UUID id, LocalTime horario) {
        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();

        horarioDisponivel.setId(id);
        horarioDisponivel.setHorario(horario);

        return horarioDisponivel;
    }

    public static HorarioDisponivel create(UUID id, LocalTime horario, Profissional profissional) {
        HorarioDisponivel horarioDisponivel = create(id, horario);
        horarioDisponivel.setProfissional(profissional);

        return horarioDisponivel;
    }

    public static HorarioDisponivel create(LocalTime horario) {
        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();

        horarioDisponivel.setHorario(horario);

        return horarioDisponivel;
    }

    public static HorarioDisponivel create(LocalTime horario, Profissional profissional) {
        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();

        horarioDisponivel.setHorario(horario);
        horarioDisponivel.setProfissional(profissional);

        return horarioDisponivel;
    }

}
