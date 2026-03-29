package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;

import java.util.List;
import java.util.UUID;

public interface IServicoGateway {

    List<ServicoOferecido> buscarServicosOferecidos(Estabelecimento estabelecimento);

    ServicoOferecido buscarServicoOferecido(UUID idServicoOferecido);
}
