package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

public interface IAdicionarEstabelecimentoValidation {

    void validate(Estabelecimento estabelecimento);
}
