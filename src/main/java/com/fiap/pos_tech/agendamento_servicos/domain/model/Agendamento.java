package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Agendamento {

    private UUID id;
    private LocalDateTime horario;
    private ServicoOferecido servicoOferecido;
    private Estabelecimento estabelecimento;
    private Profissional profissional;
    private StatusAgendamentoEnum statusAgendamentoEnum;
    private Cliente cliente;

    public static Agendamento create(UUID id,
                                     LocalDateTime horario,
                                     ServicoOferecido servicoOferecido,
                                     Estabelecimento estabelecimento,
                                     Profissional profissional,
                                     StatusAgendamentoEnum status
    ) {
        Agendamento agendamento = new Agendamento();

        agendamento.setId(id);
        agendamento.setHorario(horario);
        agendamento.setServicoOferecido(servicoOferecido);
        agendamento.setEstabelecimento(estabelecimento);
        agendamento.setProfissional(profissional);
        agendamento.setStatusAgendamentoEnum(status);

        return agendamento;
    }

    public static Agendamento create(LocalDateTime horario,
                                     ServicoOferecido servicoOferecido,
                                     Estabelecimento estabelecimento,
                                     Profissional profissional,
                                     StatusAgendamentoEnum status,
                                     Cliente cliente
    ) {
        Agendamento agendamento = new Agendamento();

        agendamento.setHorario(horario);
        agendamento.setServicoOferecido(servicoOferecido);
        agendamento.setEstabelecimento(estabelecimento);
        agendamento.setProfissional(profissional);
        agendamento.setStatusAgendamentoEnum(status);
        agendamento.setCliente(cliente);

        return agendamento;
    }

}
