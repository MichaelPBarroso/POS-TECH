package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EstabelecimentoGateway implements IEstabelecimentoGateway {

    @Override
    public Estabelecimento criarEstabelecimento(Estabelecimento estabelecimento) {
        return null;
    }

    @Override
    public List<Estabelecimento> buscarEstabelecimentos(Estabelecimento estabelecimento) {
        return List.of();
    }

    @Override
    public Estabelecimento buscarEstabelecimento(UUID idEstabelecimento) {
        return null;
    }
}
