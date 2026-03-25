package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

public interface IProfissionalGateway {

    Profissional criarProfissional(Profissional profissional);
}
