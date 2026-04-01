package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FiltroAvancado {

    private UUID id;
    private String nome;
    private LocalTime horarioAbertura;
    private LocalTime horarioFechamento;

    private Integer notaMaiorQue;
    private Integer notaMenorQue;

    private Endereco endereco;

    private Profissional profissional;
    private ServicoOferecido servicoOferecido;

    private LocalTime horarioDisponivel;

    private BigDecimal valorMaiorQue;
    private BigDecimal valorMenorQue;


    public static FiltroAvancado create(
            UUID id,
            String nome,
            LocalTime horarioAbertura,
            LocalTime horarioFechamento,
            Integer notaMaiorQue,
            Integer notaMenorQue,
            Endereco endereco,
            Profissional profissional,
            ServicoOferecido servicoOferecido,
            LocalTime horarioDisponivel,
            BigDecimal valorMaiorQue,
            BigDecimal valorMenorQue
    ){
        FiltroAvancado filtro = new FiltroAvancado();

        filtro.setId(id);
        filtro.setNome(nome);
        filtro.setHorarioAbertura(horarioAbertura);
        filtro.setHorarioFechamento(horarioFechamento);
        filtro.setNotaMaiorQue(notaMaiorQue);
        filtro.setNotaMenorQue(notaMenorQue);
        filtro.setEndereco(endereco);
        filtro.setProfissional(profissional);
        filtro.setServicoOferecido(servicoOferecido);
        filtro.setHorarioDisponivel(horarioDisponivel);
        filtro.setValorMaiorQue(valorMaiorQue);
        filtro.setValorMenorQue(valorMenorQue);


        return filtro;
    }

}
