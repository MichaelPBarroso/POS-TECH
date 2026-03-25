package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimentoUseCase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimentoUseCase.dto.InputBuscarEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimentoUseCase.dto.OutputBuscarEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

import java.util.List;

public class BuscarEstabelecimentoUseCase {

    private final IEstabelecimentoGateway estabelecimentoGateway;

    private BuscarEstabelecimentoUseCase(IEstabelecimentoGateway estabelecimentoGateway){
        this.estabelecimentoGateway = estabelecimentoGateway;
    }

    public static BuscarEstabelecimentoUseCase create(IEstabelecimentoGateway estabelecimentoGateway){
        return new BuscarEstabelecimentoUseCase(estabelecimentoGateway);
    }

    public OutputBuscarEstabelecimento buscarEstabelecimentoPorId(InputBuscarEstabelecimento input){

        Estabelecimento entity = input.toEntity();

        List<Estabelecimento> estabelecimentos = estabelecimentoGateway.buscarEstabelecimentos(entity);


        return new OutputBuscarEstabelecimento();

    }
}
