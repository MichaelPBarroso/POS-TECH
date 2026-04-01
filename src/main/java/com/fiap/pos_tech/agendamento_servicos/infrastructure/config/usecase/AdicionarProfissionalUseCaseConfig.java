package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.AdicionarProfissionalUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.AdicionarProfissionalValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.EstabelecimentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.IAdicionarProfissionalValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdicionarProfissionalUseCaseConfig {

    @Bean(name = "EstabelecimentoNaoExisteAddProfissional")
    public EstabelecimentoNaoExisteHandler  estabelecimentoNaoExisteHandler(IEstabelecimentoGateway estabelecimentoGateway) {
        return new EstabelecimentoNaoExisteHandler(estabelecimentoGateway);
    }

    @Bean
    public AdicionarProfissionalValidationChain adicionarProfissionalValidationChain(List<IAdicionarProfissionalValidation> adicionarProfissionalValidations){
        return new AdicionarProfissionalValidationChain(adicionarProfissionalValidations);
    }

    @Bean
    public AdicionarProfissionalUseCase adicionarProfissionalUseCase(
            IProfissionalGateway profissionalGateway,
            IEstabelecimentoGateway estabelecimentoGateway,
            AdicionarProfissionalValidationChain adicionarProfissionalValidationChain
    ){
        return AdicionarProfissionalUseCase.create(profissionalGateway, adicionarProfissionalValidationChain, estabelecimentoGateway);
    }
}
