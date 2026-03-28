package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.ReagendarAgendamenoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation.AgendamentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation.IReagendarAgendamentoValidation;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.validation.ReagendarAgendamentoValidationChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ReagendarAgendamentoUseCaseConfig {

    @Bean
    public AgendamentoNaoExisteHandler agendamentoNaoExisteHandler(IAgendamentoGateway agendamentoGateway) {
        return new AgendamentoNaoExisteHandler(agendamentoGateway);
    }

    @Bean
    public ReagendarAgendamentoValidationChain reagendarAgendamentoValidationChain(List<IReagendarAgendamentoValidation> reagendarAgendamentoValidations){
        return new ReagendarAgendamentoValidationChain(reagendarAgendamentoValidations);
    }

    @Bean
    public ReagendarAgendamenoUseCase reagendarAgendamenoUseCase(
            IAgendamentoGateway agendamentoGateway,
            ReagendarAgendamentoValidationChain reagendarAgendamentoValidationChain
    ) {
        return ReagendarAgendamenoUseCase.create(agendamentoGateway, reagendarAgendamentoValidationChain);
    }
}
