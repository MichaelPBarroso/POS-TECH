package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

public interface IAdicionarProfissionalValidation {

    void validate(Profissional profissional);
}
