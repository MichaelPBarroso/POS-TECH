package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EstabelecimentoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ProfissionalEntity;

import java.util.List;

public class EstabelecimentoPresenter {

    public static Estabelecimento toDomain(EstabelecimentoEntity estabelecimentoEntity) {
        return Estabelecimento.create(
                estabelecimentoEntity.getId(),
                estabelecimentoEntity.getNome(),
                estabelecimentoEntity.getHorarioAbertura(),
                estabelecimentoEntity.getHorarioFechamento(),
                EnderecoPresenter.toDomain(estabelecimentoEntity.getEndereco())
        );
    }

    public static EstabelecimentoEntity toEntity(Estabelecimento estabelecimento) {
        return EstabelecimentoEntity.builder()
                .id(estabelecimento.getId())
                .nome(estabelecimento.getNome())
                .horarioAbertura(estabelecimento.getHorarioAbertura())
                .horarioFechamento(estabelecimento.getHorarioFechamento())
                .endereco(EnderecoPresenter.toEntity(estabelecimento.getEndereco()))
                .build();
    }


    public static Estabelecimento toDomainComProfissionais(EstabelecimentoEntity estabelecimentoEntity) {
        List<Profissional> profissionais = estabelecimentoEntity.getProfissionais().stream().map(ProfissionalPresenter::toDomain).toList();

        return Estabelecimento.create(
                estabelecimentoEntity.getId(),
                estabelecimentoEntity.getNome(),
                estabelecimentoEntity.getHorarioAbertura(),
                estabelecimentoEntity.getHorarioFechamento(),
                EnderecoPresenter.toDomain(estabelecimentoEntity.getEndereco()),
                profissionais
        );
    }

    public static EstabelecimentoEntity toEntityComProfissionais(Estabelecimento estabelecimento) {
        List<ProfissionalEntity> profissionaisEntity = estabelecimento.getProfissionais().stream().map(ProfissionalPresenter::toEntity).toList();

        return EstabelecimentoEntity.builder()
                .id(estabelecimento.getId())
                .nome(estabelecimento.getNome())
                .horarioAbertura(estabelecimento.getHorarioAbertura())
                .horarioFechamento(estabelecimento.getHorarioFechamento())
                .endereco(EnderecoPresenter.toEntity(estabelecimento.getEndereco()))
                .profissionais(profissionaisEntity)
                .build();
    }


}
