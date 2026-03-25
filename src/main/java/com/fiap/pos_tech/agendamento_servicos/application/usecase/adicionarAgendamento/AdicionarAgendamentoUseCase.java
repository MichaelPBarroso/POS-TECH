package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.InputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.OutputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.AdicionarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;

public class AdicionarAgendamentoUseCase {

    private final IAgendamentoGateway adicionarAgendamentoGateway;
    private final AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain;

    private AdicionarAgendamentoUseCase(IAgendamentoGateway adicionarAgendamentoGateway, AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain) {
        this.adicionarAgendamentoGateway = adicionarAgendamentoGateway;
        this.adicionarAgendamentoValidationChain = adicionarAgendamentoValidationChain;
    }

    public static AdicionarAgendamentoUseCase create(IAgendamentoGateway adicionarAgendamentoGateway, AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain) {
        return new AdicionarAgendamentoUseCase(adicionarAgendamentoGateway, adicionarAgendamentoValidationChain);
    }

    public OutputAdicionarAgendamento execute(InputAdicionarAgendamento input) {
        Agendamento agendamento = input.toEntity();

        adicionarAgendamentoValidationChain.validate(agendamento);

        adicionarAgendamentoGateway.criarAgendamento(agendamento);

        adicionarAgendamentoGateway.enviarNotificacaoAgendamento(agendamento);

        return new OutputAdicionarAgendamento();

    }
}
