package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

import java.util.List;

public interface IEstabelecimentoGateway {

    Estabelecimento criarEstabelecimento(Estabelecimento estabelecimento);

    List<Estabelecimento> buscarEstabelecimentos(Estabelecimento estabelecimento);
}
