package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAvaliacaoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AvaliacaoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.AvaliacaoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.EstabelecimentoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.AvaliacaoPresenter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AvaliacaoGateway implements IAvaliacaoGateway {

    private final AvaliacaoJPARepository avaliacaoJPARepository;
    private final EstabelecimentoJPARepository estabelecimentoJPARepository;

    @Override
    public Avaliacao criarAvaliacao(Avaliacao avaliacao) {
        AvaliacaoEntity entity = avaliacaoJPARepository.save(AvaliacaoPresenter.toEntity(avaliacao));

        return AvaliacaoPresenter.toDomain(entity);
    }

    @Override
    public void atualizarMediaNotasAvaliacao(UUID idEstabelecimento) {

        BigDecimal mediaNotas = BigDecimal.ZERO;

        List<AvaliacaoEntity> avaliacoes = avaliacaoJPARepository.findAllByAgendamento_Estabelecimento_Id(idEstabelecimento);

        OptionalDouble average = avaliacoes.stream().mapToDouble(AvaliacaoEntity::getNota).average();

        if (average.isPresent()) {
            mediaNotas = BigDecimal.valueOf(average.getAsDouble());
        }

        estabelecimentoJPARepository.atualizarMediaNotasEstabelecimento(idEstabelecimento, mediaNotas);

    }
}
