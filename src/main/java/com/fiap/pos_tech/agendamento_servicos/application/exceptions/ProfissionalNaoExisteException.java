package com.fiap.pos_tech.agendamento_servicos.application.exceptions;

public class ProfissionalNaoExisteException extends RuntimeException {
    private static final String message = "Profissional não encontrado";

    public ProfissionalNaoExisteException() {
        super(message);
    }
}
