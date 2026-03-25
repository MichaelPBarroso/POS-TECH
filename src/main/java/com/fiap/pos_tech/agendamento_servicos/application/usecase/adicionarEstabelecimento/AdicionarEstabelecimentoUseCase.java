package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.InputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.OutputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.validation.AdicionarEstabelecimentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;

public class AdicionarEstabelecimentoUseCase {

    private final IEstabelecimentoGateway adicionarEstabelecimentoGateway;
    private final AdicionarEstabelecimentoValidationChain adicionarEstabelecimentoValidationChain;

    private AdicionarEstabelecimentoUseCase(IEstabelecimentoGateway adicionarEstabelecimentoGateway, AdicionarEstabelecimentoValidationChain adicionarEstabelecimentoValidationChain) {
        this.adicionarEstabelecimentoGateway = adicionarEstabelecimentoGateway;
        this.adicionarEstabelecimentoValidationChain = adicionarEstabelecimentoValidationChain;
    }

    public static AdicionarEstabelecimentoUseCase create(IEstabelecimentoGateway adicionarEstabelecimentoGateway, AdicionarEstabelecimentoValidationChain adicionarEstabelecimentoValidationChain) {
        return new AdicionarEstabelecimentoUseCase(adicionarEstabelecimentoGateway, adicionarEstabelecimentoValidationChain);
    }

    public OutputEstabelecimento execute(InputEstabelecimento inputEstabelecimento) {
        Estabelecimento estabelecimento = inputEstabelecimento.toEntity();

        adicionarEstabelecimentoValidationChain.validate(estabelecimento);

        adicionarEstabelecimentoGateway.criarEstabelecimento(estabelecimento);

        return new OutputEstabelecimento();
    }
}
