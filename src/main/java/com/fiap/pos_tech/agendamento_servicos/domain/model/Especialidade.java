package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class Especialidade {

    private UUID id;
    private String nome;
    private Profissional profissional;

    public static Especialidade create(UUID id, String nome) {
        Especialidade especialidade = new Especialidade();

        especialidade.setId(id);
        especialidade.setNome(nome);

        return especialidade;
    }

    public static Especialidade create(String nome) {
        Especialidade especialidade = new Especialidade();

        especialidade.setNome(nome);

        return especialidade;
    }

    public static Especialidade create(UUID id, String nome, Profissional profissional) {
        Especialidade especialidade = new Especialidade();

        especialidade.setId(id);
        especialidade.setNome(nome);
        especialidade.setProfissional(profissional);

        return especialidade;
    }
}
