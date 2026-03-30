package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ProfissionalEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.ProfissionalJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.ProfissionalPresenter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ProfissionalGateway implements IProfissionalGateway {

    private final ProfissionalJPARepository profissionalJPARepository;

    @Override
    public Profissional criarProfissional(Profissional profissional) {

        ProfissionalEntity profissionalEntity = profissionalJPARepository.save(ProfissionalPresenter.toEntityComLists(profissional));

        return ProfissionalPresenter.toDomainComLists(profissionalEntity);
    }

    @Override
    public Profissional buscarProfissional(UUID idProfissional) {
        Optional<ProfissionalEntity> profissionalEntity = profissionalJPARepository.findById(idProfissional);

        return profissionalEntity.map(ProfissionalPresenter::toDomain).orElse(null);
    }
}
