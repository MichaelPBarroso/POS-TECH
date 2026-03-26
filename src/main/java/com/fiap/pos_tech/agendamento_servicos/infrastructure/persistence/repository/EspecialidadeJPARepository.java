package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository;

import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EspecialidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EspecialidadeJPARepository extends JpaRepository<EspecialidadeEntity, UUID> {
}
