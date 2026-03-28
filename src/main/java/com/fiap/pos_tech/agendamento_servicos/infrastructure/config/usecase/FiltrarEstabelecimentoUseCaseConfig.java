package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.filtrarEstabelecimento.FiltrarEstabelecimentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltrarEstabelecimentoUseCaseConfig {

    @Bean
    public FiltrarEstabelecimentoUseCase filtrarEstabelecimentoUseCase(
            IEstabelecimentoGateway estabelecimentoGateway
    ){
        return FiltrarEstabelecimentoUseCase.create(estabelecimentoGateway);
    }

}
