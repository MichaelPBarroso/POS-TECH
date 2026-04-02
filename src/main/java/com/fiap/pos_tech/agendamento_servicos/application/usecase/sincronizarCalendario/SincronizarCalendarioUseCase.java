package com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendario;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendario.dto.InputSincronizarCalendario;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendario.dto.OutputSincronizarCalendario;

import java.util.UUID;

public class SincronizarCalendarioUseCase {

    private final IAgendamentoGateway agendamentoGateway;

    private SincronizarCalendarioUseCase(IAgendamentoGateway agendamentoGateway) {
        this.agendamentoGateway = agendamentoGateway;
    }

    public static SincronizarCalendarioUseCase create(IAgendamentoGateway agendamentoGateway) {
        return new SincronizarCalendarioUseCase(agendamentoGateway);
    }

    public OutputSincronizarCalendario execute(InputSincronizarCalendario input) {
        //Implementação não realizada, por não estar dentro dos requisitos da fase 3.
        //De acordo o que foi informado pelo coordenador, não necessitar sem implementado.

        agendamentoGateway.sincronizarCalendario(UUID.randomUUID());

        return new OutputSincronizarCalendario();
    }
}
