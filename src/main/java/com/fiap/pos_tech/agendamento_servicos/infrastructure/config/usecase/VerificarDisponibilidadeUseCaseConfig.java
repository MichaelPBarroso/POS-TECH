package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
'import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.VerificarDisponibilidadeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerificarDisponibilidadeUseCaseConfig {

    @Bean
    public VerificarDisponibilidadeUseCase verificarDisponibilidadeUseCase(
            IAgendamentoGateway agendamentoGateway
    ) {
        return VerificarDisponibilidadeUseCase.create(agendamentoGateway);
    }
}
