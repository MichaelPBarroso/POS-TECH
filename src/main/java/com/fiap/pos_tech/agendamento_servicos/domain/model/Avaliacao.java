package com.fiap.pos_tech.agendamento_servicos.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Avaliacao {

    private UUID id;
    private Agendamento agendamento;
    private Long nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;

    public static Avaliacao create(UUID id, Agendamento agendamento,  Long nota, String comentario, LocalDateTime dataAvaliacao) {
        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setId(id);
        avaliacao.setAgendamento(agendamento);
        avaliacao.setNota(nota);
        avaliacao.setComentario(comentario);
        avaliacao.setDataAvaliacao(dataAvaliacao);

        return avaliacao;
    }
}
