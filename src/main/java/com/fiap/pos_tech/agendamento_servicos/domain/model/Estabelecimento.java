package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Estabelecimento {

    private UUID id;
    private String nome;
    private LocalTime horarioAbertura;
    private LocalTime horarioFechamento;
    private Endereco endereco;
    private List<Profissional> profissionais;

    //TODO: Falta adicionar as fotos
    //TODO: Verificar se vai adicionar a nota media do estabelecimento, que é atualizado através da avaliação.

    public static Estabelecimento create(UUID id, String nome, LocalTime horarioAbertura, LocalTime horarioFechamento, Endereco endereco, List<Profissional> profissionais) {
        Estabelecimento estabelecimento = new Estabelecimento();

        estabelecimento.setId(id);
        estabelecimento.setNome(nome);
        estabelecimento.setHorarioAbertura(horarioAbertura);
        estabelecimento.setHorarioFechamento(horarioFechamento);
        estabelecimento.setEndereco(endereco);
        estabelecimento.setProfissionais(profissionais);

        return estabelecimento;
    }

    public static Estabelecimento create(UUID id, String nome, LocalTime horarioAbertura, LocalTime horarioFechamento, Endereco endereco) {
        Estabelecimento estabelecimento = new Estabelecimento();

        estabelecimento.setId(id);
        estabelecimento.setNome(nome);
        estabelecimento.setHorarioAbertura(horarioAbertura);
        estabelecimento.setHorarioFechamento(horarioFechamento);
        estabelecimento.setEndereco(endereco);

        return estabelecimento;
    }

    public static Estabelecimento create(String nome, LocalTime horarioAbertura, LocalTime horarioFechamento,  Endereco endereco) {
        Estabelecimento estabelecimento = new Estabelecimento();

        estabelecimento.setNome(nome);
        estabelecimento.setHorarioAbertura(horarioAbertura);
        estabelecimento.setHorarioFechamento(horarioFechamento);
        estabelecimento.setEndereco(endereco);

        return estabelecimento;
    }

}
