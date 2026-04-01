package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IFotoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.AdicionarFotoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation.AdicionarFotoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation.EstabelecimentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation.IAdicionarFotoValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdicionarFotoUseCaseConfig {

    @Bean(name = "EstabelecimentoNaoExisteAddFoto")
    public EstabelecimentoNaoExisteHandler estabelecimentoNaoExisteHandler(IEstabelecimentoGateway estabelecimentoGateway) {
        return new EstabelecimentoNaoExisteHandler(estabelecimentoGateway);
    }

    @Bean
    public AdicionarFotoValidationChain adicionarFotoValidationChain(List<IAdicionarFotoValidation> adicionarFotoValidationChain) {
        return new AdicionarFotoValidationChain(adicionarFotoValidationChain);
    }

    @Bean
    public AdicionarFotoUseCase adicionarFotoUseCase(
            IFotoGateway fotoGateway,
            IEstabelecimentoGateway estabelecimentoGateway,
            AdicionarFotoValidationChain validationChain
    ) {
        return AdicionarFotoUseCase.create(fotoGateway, estabelecimentoGateway, validationChain);
    }

}
