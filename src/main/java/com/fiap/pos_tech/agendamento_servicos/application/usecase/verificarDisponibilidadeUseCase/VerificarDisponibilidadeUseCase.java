package com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidadeUseCase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidadeUseCase.dto.InputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidadeUseCase.dto.OutputVerificarDisponibilidade;

public class VerificarDisponibilidadeUseCase {

    private IAgendamentoGateway agendamentoGateway;

    private VerificarDisponibilidadeUseCase(IAgendamentoGateway agendamentoGateway){
        this.agendamentoGateway = agendamentoGateway;
    }

    public static VerificarDisponibilidadeUseCase create(IAgendamentoGateway agendamentoGateway){
        return new VerificarDisponibilidadeUseCase(agendamentoGateway);
    }

    public OutputVerificarDisponibilidade execute(InputVerificarDisponibilidade input){


        return new OutputVerificarDisponibilidade();
    }

}
