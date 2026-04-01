package com.fiap.pos_tech.agendamento_servicos.application.exceptions;

public class EstabelecimentoNaoExisteException extends RuntimeException {
    private static final String message = "Estabelecimento não encontrado";

    public EstabelecimentoNaoExisteException() {
        super(message);
    }
}
