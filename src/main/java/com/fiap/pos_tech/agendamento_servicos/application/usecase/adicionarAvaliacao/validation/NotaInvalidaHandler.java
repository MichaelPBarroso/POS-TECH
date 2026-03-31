package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AvaliacaoNotaInvalidaException;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

public class NotaInvalidaHandler implements IAdicionarAvaliacaoValidation{

    @Override
    public void validate(Avaliacao avaliacao) {

        if (avaliacao.getNota() > 5 || avaliacao.getNota() < 0) {
            throw new AvaliacaoNotaInvalidaException();
        }

    }
}
