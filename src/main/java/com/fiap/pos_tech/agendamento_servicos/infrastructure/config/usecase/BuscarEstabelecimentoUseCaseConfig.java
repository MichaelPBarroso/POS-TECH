package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimentoUseCase.BuscarEstabelecimentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuscarEstabelecimentoUseCaseConfig {

    @Bean
    public BuscarEstabelecimentoUseCase buscarEstabelecimentoUseCase(
            IEstabelecimentoGateway estabelecimentoGateway
    ) {
        return BuscarEstabelecimentoUseCase.create(estabelecimentoGateway);
    }

}
