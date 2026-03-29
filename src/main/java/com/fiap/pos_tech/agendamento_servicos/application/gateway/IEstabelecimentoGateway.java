package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

import java.util.List;
import java.util.UUID;

public interface IEstabelecimentoGateway {

    Estabelecimento criarEstabelecimento(Estabelecimento estabelecimento);

    List<Estabelecimento> buscarEstabelecimentos(Estabelecimento estabelecimento);

    Estabelecimento buscarEstabelecimento(UUID idEstabelecimento);
}
