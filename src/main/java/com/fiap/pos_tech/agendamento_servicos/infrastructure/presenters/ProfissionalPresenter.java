package com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Especialidade;
import com.fiap.pos_tech.agendamento_servicos.domain.model.HorarioDisponivel;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EspecialidadeEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.HorarioDisponivelEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ProfissionalEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ServicoOferecidoEntity;

import java.util.List;

public class ProfissionalPresenter {

    public static Profissional toDomain(ProfissionalEntity profissionalEntity){
        return Profissional.create(
                profissionalEntity.getId(),
                profissionalEntity.getNome(),
                profissionalEntity.getEmail(),
                EstabelecimentoPresenter.toDomain(profissionalEntity.getEstabelecimento())
        );
    }

    public static Profissional toDomainComLists(ProfissionalEntity profissionalEntity){
        List<Especialidade> especialidades = profissionalEntity.getEspecialidades().stream().map(EspecialidadePresenter::toDomain).toList();
        List<ServicoOferecido> servicoOferecidos = profissionalEntity.getServicoOferecidos().stream().map(ServicoOferecidoPresenter::toDomain).toList();
        List<HorarioDisponivel> horariosDisponiveis = profissionalEntity.getHorariosDisponiveis().stream().map(HorarioDisponivelPresenter::toDomainProfissional).toList();


        return Profissional.create(
                profissionalEntity.getId(),
                profissionalEntity.getNome(),
                profissionalEntity.getEmail(),
                especialidades,
                servicoOferecidos,
                EstabelecimentoPresenter.toDomain(profissionalEntity.getEstabelecimento()),
                horariosDisponiveis
        );
    }

    public static ProfissionalEntity toEntity(Profissional profissional){
        return ProfissionalEntity.builder()
                .id(profissional.getId())
                .nome(profissional.getNome())
                .email(profissional.getEmail())
                .estabelecimento(EstabelecimentoPresenter.toEntity(profissional.getEstabelecimento()))
                .build();
    }

    public static ProfissionalEntity toEntityComLists(Profissional profissional){
        List<EspecialidadeEntity> especialidadeEntities = profissional.getEspecialidades().stream().map(EspecialidadePresenter::toEntity).toList();
        List<ServicoOferecidoEntity> servicoOferecidoEntities = profissional.getServicoOferecidos().stream().map(ServicoOferecidoPresenter::toEntity).toList();
        List<HorarioDisponivelEntity> horarioDisponivelEntities = profissional.getHorarioDisponivel().stream().map(HorarioDisponivelPresenter::toEntity).toList();

        ProfissionalEntity profissionalEntity = ProfissionalEntity.builder()
                .id(profissional.getId())
                .nome(profissional.getNome())
                .email(profissional.getEmail())
                .especialidades(especialidadeEntities)
                .servicoOferecidos(servicoOferecidoEntities)
                .horariosDisponiveis(horarioDisponivelEntities)
                .estabelecimento(EstabelecimentoPresenter.toEntity(profissional.getEstabelecimento()))
                .build();

        especialidadeEntities.forEach(especialidade -> especialidade.setProfissional(profissionalEntity));
        servicoOferecidoEntities.forEach(servico -> servico.setProfissional(profissionalEntity));
        horarioDisponivelEntities.forEach(horario -> horario.setProfissional(profissionalEntity));

        return profissionalEntity;
    }


}
