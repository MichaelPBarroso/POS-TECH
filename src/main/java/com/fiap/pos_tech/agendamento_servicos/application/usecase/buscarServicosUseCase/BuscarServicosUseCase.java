package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarServicosUseCase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IServicoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarServicosUseCase.dto.InputBuscarServicos;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarServicosUseCase.dto.OutputBuscarServicos;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;

import java.util.List;

public class BuscarServicosUseCase {

    private final IServicoGateway servicoGateway;

    private BuscarServicosUseCase(IServicoGateway servicoGateway) {
        this.servicoGateway = servicoGateway;
    }

    public static BuscarServicosUseCase create(IServicoGateway servicoGateway) {
        return new BuscarServicosUseCase(servicoGateway);
    }

    public OutputBuscarServicos execute(InputBuscarServicos input) {

        List<ServicoOferecido> servicoOferecidos = servicoGateway.buscarServicosOferecidos(input.estabelecimento());

        return new OutputBuscarServicos();
    }
}
