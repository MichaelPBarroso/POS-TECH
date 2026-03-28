package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.notificarAgendamento.NotificarAgendamentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificarAgendamentoUseCaseConfig {

    @Bean
    public NotificarAgendamentoUseCase notificarAgendamentoUseCase(
            IAgendamentoGateway agendamentoGateway
    ) {
        return NotificarAgendamentoUseCase.create(agendamentoGateway);
    }
}
