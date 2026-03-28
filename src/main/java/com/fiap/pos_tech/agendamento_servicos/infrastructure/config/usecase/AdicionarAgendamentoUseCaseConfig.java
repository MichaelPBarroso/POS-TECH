package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.AdicionarAgendamentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.AdicionarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.AgendamentoExistenteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.IAdicionarAgendamentoValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdicionarAgendamentoUseCaseConfig {

    @Bean
    public AgendamentoExistenteHandler agendamentoExistenteHandler(IAgendamentoGateway agendamentoGateway) {
        return new AgendamentoExistenteHandler(agendamentoGateway);
    }

    @Bean
    public AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain(List<IAdicionarAgendamentoValidation> adicionarAgendamentoValidation) {
        return new AdicionarAgendamentoValidationChain(adicionarAgendamentoValidation);
    }

    @Bean
    public AdicionarAgendamentoUseCase adicionarAgendamentoUseCase(
            IAgendamentoGateway agendamentoGateway,
            AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain
    ){
        return AdicionarAgendamentoUseCase.create(agendamentoGateway, adicionarAgendamentoValidationChain);
    }

}
