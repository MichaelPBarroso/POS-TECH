package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AgendamentoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.AgendamentoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.AgendamentoPresenter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AgendamentoGateway implements IAgendamentoGateway {

    private final AgendamentoJPARepository agendamentoJPARepository;

    @Override
    public Agendamento criarAgendamento(Agendamento agendamento) {
        AgendamentoEntity entity = agendamentoJPARepository.save(AgendamentoPresenter.toEntity(agendamento));

        return AgendamentoPresenter.toDomain(entity);
    }

    @Override
    public void enviarNotificacaoAgendamento(Agendamento agendamento) {

    }

    @Override
    public Agendamento atualizarAgendamento(Agendamento agendamento) {
        AgendamentoEntity entity = agendamentoJPARepository.save(AgendamentoPresenter.toEntity(agendamento));

        return AgendamentoPresenter.toDomain(entity);
    }

    @Transactional
    @Override
    public Agendamento cancelarAgendamento(UUID agendamentoId) {
        agendamentoJPARepository.atualizarStatusAgendamento(agendamentoId, StatusAgendamentoEnum.CANCELADO);

        Optional<AgendamentoEntity> entity = agendamentoJPARepository.findById(agendamentoId);

        return entity.map(AgendamentoPresenter::toDomain).orElse(null);
    }

    @Transactional
    @Override
    public Agendamento registrarAusenciaAgendamento(UUID agendamentoId) {
        agendamentoJPARepository.atualizarStatusAgendamento(agendamentoId, StatusAgendamentoEnum.AUSENTE);

        Optional<AgendamentoEntity> entity = agendamentoJPARepository.findById(agendamentoId);

        return entity.map(AgendamentoPresenter::toDomain).orElse(null);
    }

    @Override
    public Agendamento buscarAgendamento(Estabelecimento estabelecimento, LocalDateTime horario, Profissional profissional) {
        return null;
    }

    @Override
    public Agendamento buscarAgendamento(UUID agendamentoId) {
        Optional<AgendamentoEntity> entity = agendamentoJPARepository.findById(agendamentoId);

        return entity.map(AgendamentoPresenter::toDomain).orElse(null);
    }
}
