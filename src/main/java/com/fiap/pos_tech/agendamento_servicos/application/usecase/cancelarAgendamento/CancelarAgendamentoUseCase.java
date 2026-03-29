package com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.dto.InputCancelarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.dto.OutputCancelarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation.CancelarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class CancelarAgendamentoUseCase {

    private final IAgendamentoGateway agendamentoGateway;
    private final CancelarAgendamentoValidationChain cancelarAgendamentoValidationChain;

    private CancelarAgendamentoUseCase(IAgendamentoGateway agendamentoGateway, CancelarAgendamentoValidationChain validationChain) {
        this.agendamentoGateway = agendamentoGateway;
        this.cancelarAgendamentoValidationChain = validationChain;
    }

    public static CancelarAgendamentoUseCase create(IAgendamentoGateway agendamentoGateway,  CancelarAgendamentoValidationChain validationChain) {
        return new CancelarAgendamentoUseCase(agendamentoGateway, validationChain);
    }

    public OutputCancelarAgendamento execute(InputCancelarAgendamento input) {
        Agendamento entity = agendamentoGateway.buscarAgendamento(input.idAgendamento());

        cancelarAgendamentoValidationChain.validate(entity);

        agendamentoGateway.cancelarAgendamento(entity.getId());

        return new OutputCancelarAgendamento();
    }

}
