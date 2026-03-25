package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

public interface IAdicionarAvaliacaoValidation {

    void validate(Avaliacao avaliacao);
}
