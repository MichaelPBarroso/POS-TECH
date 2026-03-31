package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository;

import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.AvaliacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AvaliacaoJPARepository extends JpaRepository<AvaliacaoEntity, UUID> {

    List<AvaliacaoEntity> findAllByAgendamento_Estabelecimento_Id(UUID id);
}
