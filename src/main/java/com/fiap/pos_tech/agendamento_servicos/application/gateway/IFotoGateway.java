package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;
import org.springframework.web.multipart.MultipartFile;

public interface IFotoGateway {

    FotoEstabelecimento adicionar(FotoEstabelecimento fotoEstabelecimento, MultipartFile foto);
}
