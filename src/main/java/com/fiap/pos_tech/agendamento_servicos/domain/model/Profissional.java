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
    private String email;
    private List<Especialidade> especialidades;
    private List<ServicoOferecido> servicoOferecidos;
    private List<HorarioDisponivel> horarioDisponivel;
    private Estabelecimento estabelecimento;

    public static Profissional create(UUID id, String nome, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos){
        return create(id, nome, null, especialidades, servicoOferecidos);
    }

    public static Profissional create(UUID id, String nome, String email, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos){
        Profissional profissional = new Profissional();

        profissional.setId(id);
        profissional.setNome(nome);
        profissional.setEmail(email);
        profissional.setEspecialidades(especialidades);
        profissional.setServicoOferecidos(servicoOferecidos);

        return profissional;
    }

    public static Profissional create(UUID id, String nome, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos, Estabelecimento estabelecimento, List<HorarioDisponivel> horarioDisponivel){
        return create(id, nome, null, especialidades, servicoOferecidos, estabelecimento, horarioDisponivel);
    }

    public static Profissional create(UUID id, String nome, String email, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos, Estabelecimento estabelecimento, List<HorarioDisponivel> horarioDisponivel){
        Profissional profissional = new Profissional();

        profissional.setId(id);
        profissional.setNome(nome);
        profissional.setEmail(email);
        profissional.setEspecialidades(especialidades);
        profissional.setServicoOferecidos(servicoOferecidos);
        profissional.setEstabelecimento(estabelecimento);
        profissional.setHorarioDisponivel(horarioDisponivel);

        return profissional;
    }

    public static Profissional create(String nome, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos, Estabelecimento estabelecimento, List<HorarioDisponivel> horarioDisponivel) {
        return create(nome, null, especialidades, servicoOferecidos, estabelecimento, horarioDisponivel);
    }

    public static Profissional create(String nome, String email, List<Especialidade> especialidades,  List<ServicoOferecido> servicoOferecidos, Estabelecimento estabelecimento, List<HorarioDisponivel> horarioDisponivel) {
        Profissional profissional = new Profissional();

        profissional.setNome(nome);
        profissional.setEmail(email);
        profissional.setEspecialidades(especialidades);
        profissional.setServicoOferecidos(servicoOferecidos);
        profissional.setEstabelecimento(estabelecimento);
        profissional.setHorarioDisponivel(horarioDisponivel);

        return profissional;
    }

    public static Profissional create(UUID id, String nome, Estabelecimento estabelecimento){
        return create(id, nome, null, estabelecimento);
    }

    public static Profissional create(UUID id, String nome, String email, Estabelecimento estabelecimento){
        Profissional profissional = new Profissional();

        profissional.setId(id);
        profissional.setNome(nome);
        profissional.setEmail(email);
        profissional.setEstabelecimento(estabelecimento);

        return profissional;
    }
}
