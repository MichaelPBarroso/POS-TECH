package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.AdicionarEstabelecimentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.validation.AdicionarEstabelecimentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.validation.IAdicionarEstabelecimentoValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdicionarEstabelecimentoUseCaseConfig {

    @Bean
    public AdicionarEstabelecimentoValidationChain adicionarEstabelecimentoValidationChain(List<IAdicionarEstabelecimentoValidation> adicionarEstabelecimentoValidations){
        return new AdicionarEstabelecimentoValidationChain(adicionarEstabelecimentoValidations);
    }

    @Bean
    public AdicionarEstabelecimentoUseCase adicionarEstabelecimentoUseCase(
            IEstabelecimentoGateway estabelecimentoGateway,
            AdicionarEstabelecimentoValidationChain adicionarEstabelecimentoValidationChain
    ){
        return AdicionarEstabelecimentoUseCase.create(estabelecimentoGateway, adicionarEstabelecimentoValidationChain);
    }
}
