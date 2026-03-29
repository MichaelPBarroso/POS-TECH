package com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.dto.InputRegistarAusencia;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.dto.OutputRegistrarAusencia;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.validation.RegistrarAusenciaValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

import java.util.UUID;

public class RegistrarAusenciaUseCase {

    private final IAgendamentoGateway agendamentoGateway;
    private final RegistrarAusenciaValidationChain registrarAusenciaValidationChain;

    private RegistrarAusenciaUseCase(IAgendamentoGateway agendamentoGateway, RegistrarAusenciaValidationChain registrarAusenciaValidationChain) {
        this.agendamentoGateway = agendamentoGateway;
        this.registrarAusenciaValidationChain = registrarAusenciaValidationChain;
    }

    public static RegistrarAusenciaUseCase create(IAgendamentoGateway agendamentoGateway, RegistrarAusenciaValidationChain registrarAusenciaValidationChain) {
        return new RegistrarAusenciaUseCase(agendamentoGateway, registrarAusenciaValidationChain);
    }

    public OutputRegistrarAusencia execute(InputRegistarAusencia input) {
        Agendamento agendamento = agendamentoGateway.buscarAgendamento(UUID.fromString(input.idAgendamento()));

        registrarAusenciaValidationChain.validate(agendamento);

        agendamentoGateway.registrarAusenciaAgendamento(agendamento.getId());

        return new OutputRegistrarAusencia();
    }
}
