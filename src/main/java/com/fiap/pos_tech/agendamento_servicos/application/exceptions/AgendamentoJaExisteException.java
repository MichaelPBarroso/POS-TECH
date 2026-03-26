package com.fiap.pos_tech.agendamento_servicos.application.exceptions;

public class AgendamentoJaExisteException extends RuntimeException {
    private static final String message = "Agendamento para a data %s já cadastrado";

    public AgendamentoJaExisteException(String dataAgendamento) {
        super(message.formatted(dataAgendamento));
    }
}
