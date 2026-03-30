package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Profissional {

    private UUID id;
    private String nome;
    private List<Especialidade> especialidades;
    private List<ServicoOferecido> servicoOferecidos;
    private Estabelecimento estabelecimento;

    public static Profissional create(UUID id, String nome, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos){
        Profissional profissional = new Profissional();

        profissional.setId(id);
        profissional.setNome(nome);
        profissional.setEspecialidades(especialidades);
        profissional.setServicoOferecidos(servicoOferecidos);

        return profissional;
    }

    public static Profissional create(UUID id, String nome, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos, Estabelecimento estabelecimento){
        Profissional profissional = new Profissional();

        profissional.setId(id);
        profissional.setNome(nome);
        profissional.setEspecialidades(especialidades);
        profissional.setServicoOferecidos(servicoOferecidos);
        profissional.setEstabelecimento(estabelecimento);

        return profissional;
    }

    public static Profissional create(String nome, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos, Estabelecimento estabelecimento) {
        Profissional profissional = new Profissional();

        profissional.setNome(nome);
        profissional.setEspecialidades(especialidades);
        profissional.setServicoOferecidos(servicoOferecidos);
        profissional.setEstabelecimento(estabelecimento);

        return profissional;
    }

    public static Profissional create(UUID id, String nome, Estabelecimento estabelecimento){
        Profissional profissional = new Profissional();

        profissional.setId(id);
        profissional.setNome(nome);
        profissional.setEstabelecimento(estabelecimento);

        return profissional;
    }
}
