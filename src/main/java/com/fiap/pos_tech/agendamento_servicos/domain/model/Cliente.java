package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    private UUID id;
    private String nome;
    private String cpf;
    private String email;

    public static Cliente create(String nome, String cpf, String email) {
        Cliente cliente = new Cliente();

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);

        return cliente;
    }

    public static Cliente create(UUID id, String nome, String cpf, String email) {
        Cliente cliente = new Cliente();

        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);

        return cliente;
    }
}
