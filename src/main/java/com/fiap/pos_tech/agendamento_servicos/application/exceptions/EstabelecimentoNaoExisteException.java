package com.fiap.pos_tech.agendamento_servicos.application.exceptions;

public class EstabelecimentoNaoExisteException extends RuntimeException {
    private static final String message = "Profissional não encontrado";

    public EstabelecimentoNaoExisteException() {
        super(message);
    }
}
