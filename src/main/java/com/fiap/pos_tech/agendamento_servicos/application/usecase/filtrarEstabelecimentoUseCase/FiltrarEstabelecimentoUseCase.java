package com.fiap.pos_tech.agendamento_servicos.application.usecase.filtrarEstabelecimentoUseCase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.filtrarEstabelecimentoUseCase.dto.InputFiltrarEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.filtrarEstabelecimentoUseCase.dto.OutputFiltrarEstabelecimento;

public class FiltrarEstabelecimentoUseCase {

    private final IEstabelecimentoGateway estabelecimentoGateway;

    private FiltrarEstabelecimentoUseCase(IEstabelecimentoGateway estabelecimentoGateway) {
        this.estabelecimentoGateway = estabelecimentoGateway;
    }

    public static FiltrarEstabelecimentoUseCase create(IEstabelecimentoGateway estabelecimentoGateway) {
        return new FiltrarEstabelecimentoUseCase(estabelecimentoGateway);
    }

    public OutputFiltrarEstabelecimento execute(InputFiltrarEstabelecimento input) {


        return new OutputFiltrarEstabelecimento();
    }
}
