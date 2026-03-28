package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.sincronizarCalendario.SincronizarCalendarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SincronizarCalendarioUseCaseConfig {

    @Bean
    public SincronizarCalendarioUseCase sincronizarCalendarioUseCase(
            IAgendamentoGateway agendamentoGateway
    ){
        return SincronizarCalendarioUseCase.create(agendamentoGateway);
    }
}
