package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAvaliacaoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.AdicionarAvaliacaoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation.AdicionarAvaliacaoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation.IAdicionarAvaliacaoValidation;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation.NotaInvalidaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdicionarAvaliacaoUseCaseConfig {

    @Bean
    public NotaInvalidaHandler notaInvalidaHandler() {
        return new NotaInvalidaHandler();
    }

    @Bean
    public AdicionarAvaliacaoValidationChain adicionarAvaliacaoValidationChain(List<IAdicionarAvaliacaoValidation> adicionarAvaliacaoValidations){
        return new AdicionarAvaliacaoValidationChain(adicionarAvaliacaoValidations);
    }

    @Bean
    public AdicionarAvaliacaoUseCase adicionarAvaliacaoUseCase(
            IAvaliacaoGateway avaliacaoGateway,
            IAgendamentoGateway agendamentoGateway,
            AdicionarAvaliacaoValidationChain avaliacaoValidationChain
    ){
        return AdicionarAvaliacaoUseCase.create(avaliacaoGateway, avaliacaoValidationChain, agendamentoGateway);
    }
}
