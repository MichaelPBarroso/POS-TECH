package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IFotoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.dto.InputAdicionarFotos;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.dto.OutputAdicionarFoto;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation.AdicionarFotoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;
import org.jspecify.annotations.NonNull;

public class AdicionarFotoUseCase {

    private final IFotoGateway fotoGateway;
    private final IEstabelecimentoGateway estabelecimentoGateway;
    private final AdicionarFotoValidationChain validationChain;

    private AdicionarFotoUseCase(IFotoGateway fotoGateway, IEstabelecimentoGateway estabelecimentoGateway, AdicionarFotoValidationChain validationChain) {
        this.fotoGateway = fotoGateway;
        this.estabelecimentoGateway = estabelecimentoGateway;
        this.validationChain = validationChain;
    }

    public static AdicionarFotoUseCase create(IFotoGateway fotoGateway, IEstabelecimentoGateway estabelecimentoGateway, AdicionarFotoValidationChain validationChain) {
        return new AdicionarFotoUseCase(fotoGateway, estabelecimentoGateway, validationChain);
    }

    public OutputAdicionarFoto execute(InputAdicionarFotos inputAdicionarFotos) {

        FotoEstabelecimento fotoEstabelecimento = toDomain(inputAdicionarFotos);

        validationChain.validate(fotoEstabelecimento);

        FotoEstabelecimento fotoEstabelecimentoBd = fotoGateway.adicionar(fotoEstabelecimento, inputAdicionarFotos.foto());

        return new OutputAdicionarFoto(fotoEstabelecimentoBd.getId(), fotoEstabelecimentoBd.getUrl(), fotoEstabelecimentoBd.getEstabelecimento().getId());
    }

    private @NonNull FotoEstabelecimento toDomain(InputAdicionarFotos inputAdicionarFotos) {
        Estabelecimento estabelecimento = estabelecimentoGateway.buscarEstabelecimento(inputAdicionarFotos.idEstabelecimento());

        return FotoEstabelecimento.create(estabelecimento);
    }
}
