package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository;

import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AgendamentoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AgendamentoJPARepository extends JpaRepository<AgendamentoEntity, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE AgendamentoEntity agendamento " +
            "SET agendamento.statusAgendamentoEnum = :statusAgendamento " +
            "WHERE agendamento.id = :idAgendamento ")
    int atualizarStatusAgendamento(@Param("idAgendamento") UUID idAgendamento, @Param("statusAgendamento") StatusAgendamentoEnum statusAgendamento);

    List<AgendamentoEntity> findAllByEstabelecimento_IdAndDataAndProfissional_Id(UUID estabelecimento_id, LocalDate data, UUID profissional_id);

    AgendamentoEntity findFirstByEstabelecimento_IdAndDataAndHorarioAndProfissional_Id(UUID estabelecimento_id, LocalDate data, LocalTime horario, UUID profissional_Id);

    List<AgendamentoEntity> findAllByData(LocalDate data);
}
