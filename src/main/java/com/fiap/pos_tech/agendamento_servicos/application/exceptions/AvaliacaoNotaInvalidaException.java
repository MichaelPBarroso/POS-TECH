package com.fiap.pos_tech.agendamento_servicos.application.exceptions;

public class AvaliacaoNotaInvalidaException extends RuntimeException {
    private static final String message = "Valor da Nota invalido, a nota deve ser entre 0 e 5";

    public AvaliacaoNotaInvalidaException() {
        super(message);
    }
}
