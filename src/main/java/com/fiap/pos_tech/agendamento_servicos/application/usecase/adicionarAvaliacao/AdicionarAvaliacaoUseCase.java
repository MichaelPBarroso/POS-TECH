package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAvaliacaoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.InputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.OutputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.validation.AdicionarAvaliacaoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Avaliacao;
import org.jspecify.annotations.NonNull;

public class AdicionarAvaliacaoUseCase {

    private final IAgendamentoGateway agendamentoGateway;
    private final IAvaliacaoGateway adicionarAvaliacaoGateway;
    private final AdicionarAvaliacaoValidationChain adicionarAvaliacaoValidationChain;

    private AdicionarAvaliacaoUseCase(IAvaliacaoGateway adicionarAvaliacaoGateway, AdicionarAvaliacaoValidationChain adicionarAvaliacaoValidationChain, IAgendamentoGateway agendamentoGateway){
        this.adicionarAvaliacaoGateway = adicionarAvaliacaoGateway;
        this.adicionarAvaliacaoValidationChain = adicionarAvaliacaoValidationChain;
        this.agendamentoGateway = agendamentoGateway;
    }

    public static AdicionarAvaliacaoUseCase create(IAvaliacaoGateway adicionarAvaliacaoGateway, AdicionarAvaliacaoValidationChain adicionarAvaliacaoValidationChain, IAgendamentoGateway agendamentoGateway){
        return new AdicionarAvaliacaoUseCase(adicionarAvaliacaoGateway, adicionarAvaliacaoValidationChain, agendamentoGateway);
    }

    public OutputAdicionarAvaliacao execute(InputAdicionarAvaliacao inputAdicionarAvaliacao){
        Avaliacao entity = toEntity(inputAdicionarAvaliacao);

        adicionarAvaliacaoValidationChain.validate(entity);

        Avaliacao avaliacao = adicionarAvaliacaoGateway.criarAvaliacao(entity);

        return new OutputAdicionarAvaliacao(avaliacao.getId(), avaliacao.getNota(), avaliacao.getAgendamento().getId(), avaliacao.getComentario(), avaliacao.getDataAvaliacao());
    }

    private @NonNull Avaliacao toEntity(InputAdicionarAvaliacao inputAdicionarAvaliacao) {
        Agendamento agendamento = agendamentoGateway.buscarAgendamento(inputAdicionarAvaliacao.idAgendamento());

        return Avaliacao.create(agendamento, inputAdicionarAvaliacao.nota(), inputAdicionarAvaliacao.comentario(), inputAdicionarAvaliacao.dataAvaliacao());
    }
}
