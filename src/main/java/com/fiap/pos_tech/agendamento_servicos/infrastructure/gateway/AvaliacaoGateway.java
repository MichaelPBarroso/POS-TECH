package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAvaliacaoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AvaliacaoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.AvaliacaoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.AvaliacaoPresenter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AvaliacaoGateway implements IAvaliacaoGateway {

    private final AvaliacaoJPARepository avaliacaoJPARepository;

    @Override
    public Avaliacao criarAvaliacao(Avaliacao avaliacao) {
        AvaliacaoEntity entity = avaliacaoJPARepository.save(AvaliacaoPresenter.toEntity(avaliacao));

        return AvaliacaoPresenter.toDomain(entity);
    }
}
