package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AgendamentoEntity;

public class AgendamentoPresenter {

    public static Agendamento toDomain(AgendamentoEntity agendamentoEntity) {
        return Agendamento.create(
                agendamentoEntity.getId(),
                agendamentoEntity.getHorario(),
                ServicoOferecidoPresenter.toDomain(agendamentoEntity.getServicoOferecido()),
                EstabelecimentoPresenter.toDomain(agendamentoEntity.getEstabelecimento()),
                ProfissionalPresenter.toDomain(agendamentoEntity.getProfissional()),
                agendamentoEntity.getStatusAgendamentoEnum(),
                ClientePresenter.toDomain(agendamentoEntity.getCliente())
        );
    }

    public static AgendamentoEntity toEntity(Agendamento agendamento) {
        return AgendamentoEntity.builder()
                .id(agendamento.getId())
                .horario(agendamento.getHorario())
                .servicoOferecido(ServicoOferecidoPresenter.toEntity(agendamento.getServicoOferecido()))
                .estabelecimento(EstabelecimentoPresenter.toEntity(agendamento.getEstabelecimento()))
                .profissional(ProfissionalPresenter.toEntity(agendamento.getProfissional()))
                .statusAgendamentoEnum(agendamento.getStatusAgendamentoEnum())
                .cliente(ClientePresenter.toEntity(agendamento.getCliente()))
                .build();
    }
}
