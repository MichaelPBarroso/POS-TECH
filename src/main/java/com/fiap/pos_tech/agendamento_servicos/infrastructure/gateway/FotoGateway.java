package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IFotoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.FotoEstabelecimentoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.FotoEstabelecimentoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.FotoPresenter;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.storage.ArmazenarFoto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@AllArgsConstructor
public class FotoGateway implements IFotoGateway {

    private final ArmazenarFoto armazenarFoto;
    private final FotoEstabelecimentoJPARepository fotoEstabelecimentoJPARepository;

    @Override
    public FotoEstabelecimento adicionar(FotoEstabelecimento fotoEstabelecimento, MultipartFile foto) {

        FotoEstabelecimentoEntity entity = FotoPresenter.toEntity(fotoEstabelecimento);

        String url = armazenarFoto.uploadFile(foto);

        entity.setUrl(url);

        FotoEstabelecimentoEntity fotoEstabelecimentoEntity = fotoEstabelecimentoJPARepository.save(entity);

        return FotoPresenter.toDomain(fotoEstabelecimentoEntity);
    }
}
