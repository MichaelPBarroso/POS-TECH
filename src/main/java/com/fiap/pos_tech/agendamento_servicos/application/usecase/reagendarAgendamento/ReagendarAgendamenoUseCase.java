package com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto.InputReagendarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto.OutputReagendarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation.ReagendarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class ReagendarAgendamenoUseCase {

    private final IAgendamentoGateway agendamentoGateway;
    private final ReagendarAgendamentoValidationChain reagendarAgendamentoValidationChain;

    private ReagendarAgendamenoUseCase(IAgendamentoGateway agendamentoGateway, ReagendarAgendamentoValidationChain reagendarAgendamentoValidationChain) {
        this.agendamentoGateway = agendamentoGateway;
        this.reagendarAgendamentoValidationChain = reagendarAgendamentoValidationChain;
    }

    public static ReagendarAgendamenoUseCase create(IAgendamentoGateway agendamentoGateway, ReagendarAgendamentoValidationChain reagendarAgendamentoValidationChain) {
        return new ReagendarAgendamenoUseCase(agendamentoGateway, reagendarAgendamentoValidationChain);
    }

    public OutputReagendarAgendamento execute(InputReagendarAgendamento input) {
        Agendamento agendamento = input.toEntity();

        reagendarAgendamentoValidationChain.validate(agendamento);

        agendamentoGateway.atualizarAgendamento(agendamento);

        return new OutputReagendarAgendamento();

    }


}
