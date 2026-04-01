package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation;

import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;

public interface IAdicionarFotoValidation {

    void validate(FotoEstabelecimento fotoEstabelecimento);
}
