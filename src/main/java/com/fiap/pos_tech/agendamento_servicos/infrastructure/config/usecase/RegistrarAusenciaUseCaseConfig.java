package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.RegistrarAusenciaUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.validation.AgendamentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.validation.IRegistrarAusenciaValidation;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.validation.RegistrarAusenciaValidationChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RegistrarAusenciaUseCaseConfig {

    @Bean
    public AgendamentoNaoExisteHandler agendamentoNaoExisteHandler(IAgendamentoGateway agendamentoGateway) {
        return new AgendamentoNaoExisteHandler(agendamentoGateway);
    }

    @Bean
    public RegistrarAusenciaValidationChain registrarAusenciaValidationChain(List<IRegistrarAusenciaValidation> registrarAusenciaValidations){
        return new RegistrarAusenciaValidationChain(registrarAusenciaValidations);
    }

    @Bean
    public RegistrarAusenciaUseCase registrarAusenciaUseCase(
        IAgendamentoGateway agendamentoGateway,
        RegistrarAusenciaValidationChain registrarAusenciaValidationChain
    ){
        return RegistrarAusenciaUseCase.create(agendamentoGateway, registrarAusenciaValidationChain);
    }
}
