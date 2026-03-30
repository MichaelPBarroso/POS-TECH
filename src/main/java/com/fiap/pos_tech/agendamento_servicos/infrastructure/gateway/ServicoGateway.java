package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IServicoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ServicoOferecidoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.EstabelecimentoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.ProfissionalJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.ServicoOferecidoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.ServicoOferecidoPresenter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServicoGateway implements IServicoGateway {

    private final ServicoOferecidoJPARepository servicoOferecidoJPARepository;

    @Override
    public List<ServicoOferecido> buscarServicosOferecidos(Estabelecimento estabelecimento) {
        List<ServicoOferecidoEntity> servicoOferecidoEntities = servicoOferecidoJPARepository.findServicoOferecidoEntitiesByProfissional_Estabelecimento_Id(estabelecimento.getId());

        return servicoOferecidoEntities.stream().map(ServicoOferecidoPresenter::toDomain).toList();
    }

    @Override
    public ServicoOferecido buscarServicoOferecido(UUID idServicoOferecido) {
        Optional<ServicoOferecidoEntity> servicoOferecidoEntity = servicoOferecidoJPARepository.findById(idServicoOferecido);

        return servicoOferecidoEntity.map(ServicoOferecidoPresenter::toDomain).orElse(null);
    }
}
