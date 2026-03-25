package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ServicoOferecido {

    private UUID id;
    private String nome;
    private Double valor;
    private Profissional profissional;

    public static ServicoOferecido create(UUID id, String nome, Double valor) {
        ServicoOferecido servicoOferecido = new ServicoOferecido();

        servicoOferecido.setId(id);
        servicoOferecido.setNome(nome);
        servicoOferecido.setValor(valor);

        return servicoOferecido;
    }

    public static ServicoOferecido create(UUID id, String nome, Double valor, Profissional profissional) {
        ServicoOferecido servicoOferecido = new ServicoOferecido();

        servicoOferecido.setId(id);
        servicoOferecido.setNome(nome);
        servicoOferecido.setValor(valor);
        servicoOferecido.setProfissional(profissional);

        return servicoOferecido;
    }
}
