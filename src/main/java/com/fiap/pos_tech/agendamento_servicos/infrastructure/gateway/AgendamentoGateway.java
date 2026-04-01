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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    public Agendamento buscarAgendamento(Estabelecimento estabelecimento, LocalDate data, LocalTime horario, Profissional profissional) {
        AgendamentoEntity agendamentoEntity = agendamentoJPARepository.findFirstByEstabelecimento_IdAndDataAndHorarioAndProfissional_Id(estabelecimento.getId(), data, horario, profissional.getId());

        if(agendamentoEntity != null)
            return AgendamentoPresenter.toDomain(agendamentoEntity);

        return null;
    }

    @Override
    public List<Agendamento> buscarAgendamentos(Estabelecimento estabelecimento, LocalDate data, Profissional profissional){

        List<AgendamentoEntity> agendamentoEntities = agendamentoJPARepository.findAllByEstabelecimento_IdAndDataAndProfissional_Id(estabelecimento.getId(), data, profissional.getId());

        return agendamentoEntities.stream().map(AgendamentoPresenter::toDomain).toList();
    }

    @Override
    public Agendamento buscarAgendamento(UUID agendamentoId) {
        Optional<AgendamentoEntity> entity = agendamentoJPARepository.findById(agendamentoId);

        return entity.map(AgendamentoPresenter::toDomain).orElse(null);
    }
}
