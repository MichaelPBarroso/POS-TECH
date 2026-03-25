package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAvaliacaoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.InputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.OutputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation.AdicionarAvaliacaoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;

public class AdicionarAvaliacaoUseCase {

    private final IAvaliacaoGateway adicionarAvaliacaoGateway;
    private final AdicionarAvaliacaoValidationChain adicionarAvaliacaoValidationChain;

    private AdicionarAvaliacaoUseCase(IAvaliacaoGateway adicionarAvaliacaoGateway, AdicionarAvaliacaoValidationChain adicionarAvaliacaoValidationChain){
        this.adicionarAvaliacaoGateway = adicionarAvaliacaoGateway;
        this.adicionarAvaliacaoValidationChain = adicionarAvaliacaoValidationChain;
    }

    public static AdicionarAvaliacaoUseCase create(IAvaliacaoGateway adicionarAvaliacaoGateway, AdicionarAvaliacaoValidationChain adicionarAvaliacaoValidationChain){
        return new AdicionarAvaliacaoUseCase(adicionarAvaliacaoGateway, adicionarAvaliacaoValidationChain);
    }

    public OutputAdicionarAvaliacao execute(InputAdicionarAvaliacao inputAdicionarAvaliacao){
        Avaliacao entity = inputAdicionarAvaliacao.toEntity();

        adicionarAvaliacaoValidationChain.validate(entity);

        adicionarAvaliacaoGateway.criarAvaliacao(entity);

        return new OutputAdicionarAvaliacao();
    }
}
