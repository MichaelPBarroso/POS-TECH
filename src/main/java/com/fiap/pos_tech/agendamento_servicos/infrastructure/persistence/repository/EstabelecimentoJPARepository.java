package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository;

import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EstabelecimentoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface EstabelecimentoJPARepository extends JpaRepository<EstabelecimentoEntity, UUID>, JpaSpecificationExecutor<EstabelecimentoEntity> {


    @Modifying
    @Transactional
    @Query("UPDATE EstabelecimentoEntity estabelecimento " +
            "SET estabelecimento.mediaNotas = :mediaNotas " +
            "WHERE estabelecimento.id = :idEstabelecimento ")
    int atualizarMediaNotasEstabelecimento(@Param("idEstabelecimento") UUID idEstabelecimento, @Param("mediaNotas") BigDecimal mediaNotas);
}
