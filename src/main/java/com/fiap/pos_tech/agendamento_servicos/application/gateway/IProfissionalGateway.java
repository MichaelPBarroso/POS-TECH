package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

import java.util.UUID;

public interface IProfissionalGateway {

    Profissional criarProfissional(Profissional profissional);

    Profissional buscarProfissional(UUID idProfissional);

    Profissional buscarProfissionalECaracteriscas(UUID idProfissional);
}
