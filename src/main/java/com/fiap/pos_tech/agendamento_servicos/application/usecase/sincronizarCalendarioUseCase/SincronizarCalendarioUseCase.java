package com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendarioUseCase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.filtrarEstabelecimentoUseCase.dto.OutputFiltrarEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendarioUseCase.dto.InputSincronizarCalendario;

public class SincronizarCalendarioUseCase {

    private final IAgendamentoGateway agendamentoGateway;

    private SincronizarCalendarioUseCase(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    public static SincronizarCalendarioUseCase create(IAgendamentoGateway agendamentoGateway) {
        return new SincronizarCalendarioUseCase(agendamentoGateway);
    }

    public OutputFiltrarEstabelecimento execute(InputSincronizarCalendario input) {



        return new OutputFiltrarEstabelecimento();
    }
}
