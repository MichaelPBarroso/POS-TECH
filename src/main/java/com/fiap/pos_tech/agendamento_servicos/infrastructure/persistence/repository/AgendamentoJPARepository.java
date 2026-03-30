package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository;

import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AgendamentoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AgendamentoJPARepository extends JpaRepository<AgendamentoEntity, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE AgendamentoEntity agendamento " +
            "SET agendamento.statusAgendamentoEnum = :statusAgendamento " +
            "WHERE agendamento.id = :idAgendamento ")
    int atualizarStatusAgendamento(@Param("idAgendamento") UUID idAgendamento, @Param("statusAgendamento") StatusAgendamentoEnum statusAgendamento);
}
