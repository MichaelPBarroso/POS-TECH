package com.fiap.pos_tech.agendamento_servicos.application.exceptions;

public class AgendamentoNaoExisteException extends RuntimeException {
    private static final String message = "Agendamento não encontrado";

    public AgendamentoNaoExisteException() {
        super(message);
    }
}
