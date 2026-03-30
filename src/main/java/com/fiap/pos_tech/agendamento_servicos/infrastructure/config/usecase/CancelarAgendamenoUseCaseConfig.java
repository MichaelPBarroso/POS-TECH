package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.CancelarAgendamentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation.AgendamentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation.CancelarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.validation.ICancelarAgendamentoValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CancelarAgendamenoUseCaseConfig {

    @Bean(name = "AgendamentoNaoExisteCancelar")
    public AgendamentoNaoExisteHandler agendamentoNaoExisteHandler(IAgendamentoGateway agendamentoGateway) {
        return new AgendamentoNaoExisteHandler(agendamentoGateway);
    }

    @Bean
    public CancelarAgendamentoValidationChain cancelarAgendamentoValidationChain(List<ICancelarAgendamentoValidation> cancelarAgendamentoValidations) {
        return new CancelarAgendamentoValidationChain(cancelarAgendamentoValidations);
    }

    @Bean
    public CancelarAgendamentoUseCase cancelarAgendamentoUseCase(
            IAgendamentoGateway agendamentoGateway,
            CancelarAgendamentoValidationChain cancelarAgendamentoValidationChain
    ) {
        return CancelarAgendamentoUseCase.create(agendamentoGateway, cancelarAgendamentoValidationChain);
    }
}
