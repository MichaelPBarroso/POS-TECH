package com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendarioUseCase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendarioUseCase.dto.InputSincronizarCalendario;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendarioUseCase.dto.OutputSincronizarCalendario;

public class SincronizarCalendarioUseCase {

    private final IAgendamentoGateway agendamentoGateway;

    private SincronizarCalendarioUseCase(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    public static SincronizarCalendarioUseCase create(IAgendamentoGateway agendamentoGateway) {
        return new SincronizarCalendarioUseCase(agendamentoGateway);
    }

    public OutputSincronizarCalendario execute(InputSincronizarCalendario input) {



        return new OutputSincronizarCalendario();
    }
}
