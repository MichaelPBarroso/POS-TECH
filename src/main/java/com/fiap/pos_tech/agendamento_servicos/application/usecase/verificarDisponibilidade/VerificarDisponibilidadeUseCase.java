package com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.InputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.OutputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class VerificarDisponibilidadeUseCase {

    private IAgendamentoGateway agendamentoGateway;

    private VerificarDisponibilidadeUseCase(IAgendamentoGateway agendamentoGateway){
        this.agendamentoGateway = agendamentoGateway;
    }

    public static VerificarDisponibilidadeUseCase create(IAgendamentoGateway agendamentoGateway){
        return new VerificarDisponibilidadeUseCase(agendamentoGateway);
    }

    public OutputVerificarDisponibilidade execute(InputVerificarDisponibilidade input){

        Agendamento agendamento = agendamentoGateway.buscarAgendamento(input.estabelecimento(), input.horario(), input.profissional());

        return new OutputVerificarDisponibilidade();
    }

}
