package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository;

import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ServicoOferecidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServicoOferecidoJPARepository extends JpaRepository<ServicoOferecidoEntity, UUID> {

    List<ServicoOferecidoEntity> findServicoOferecidoEntitiesByProfissional_Estabelecimento_Id(UUID profissionalEstabelecimentoId);

}
