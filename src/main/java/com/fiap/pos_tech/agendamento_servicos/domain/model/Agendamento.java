package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Agendamento {

    private UUID id;
    private LocalDate data;
    private LocalTime horario;
    private ServicoOferecido servicoOferecido;
    private Estabelecimento estabelecimento;
    private Profissional profissional;
    private StatusAgendamentoEnum statusAgendamentoEnum;
    private Cliente cliente;

    public static Agendamento create(UUID id,
                                     LocalDate data,
                                     LocalTime horario,
                                     ServicoOferecido servicoOferecido,
                                     Estabelecimento estabelecimento,
                                     Profissional profissional,
                                     StatusAgendamentoEnum status,
                                     Cliente cliente
    ) {
        Agendamento agendamento = new Agendamento();

        agendamento.setId(id);
        agendamento.setData(data);
        agendamento.setHorario(horario);
        agendamento.setServicoOferecido(servicoOferecido);
        agendamento.setEstabelecimento(estabelecimento);
        agendamento.setProfissional(profissional);
        agendamento.setStatusAgendamentoEnum(status);
        agendamento.setCliente(cliente);

        return agendamento;
    }

    public static Agendamento create(LocalDate data,
                                     LocalTime horario,
                                     ServicoOferecido servicoOferecido,
                                     Estabelecimento estabelecimento,
                                     Profissional profissional,
                                     StatusAgendamentoEnum status,
                                     Cliente cliente
    ) {
        Agendamento agendamento = new Agendamento();

        agendamento.setData(data);
        agendamento.setHorario(horario);
        agendamento.setServicoOferecido(servicoOferecido);
        agendamento.setEstabelecimento(estabelecimento);
        agendamento.setProfissional(profissional);
        agendamento.setStatusAgendamentoEnum(status);
        agendamento.setCliente(cliente);

        return agendamento;
    }

}
