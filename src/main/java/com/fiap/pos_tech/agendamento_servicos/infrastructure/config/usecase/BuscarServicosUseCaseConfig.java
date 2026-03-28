package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IServicoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarServicosUseCase.BuscarServicosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuscarServicosUseCaseConfig {

    @Bean
    public BuscarServicosUseCase buscarServicosUseCase(
            IServicoGateway servicoGateway
    ) {
        return BuscarServicosUseCase.create(servicoGateway);
    }
}
