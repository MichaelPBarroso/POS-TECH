package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.InputEndereco;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.InputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.OutputEndereco;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.OutputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.validation.AdicionarEstabelecimentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import org.jspecify.annotations.NonNull;

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
        Estabelecimento estabelecimento = toEntity(inputEstabelecimento);

        adicionarEstabelecimentoValidationChain.validate(estabelecimento);

        Estabelecimento estabelecimentoDb = adicionarEstabelecimentoGateway.criarEstabelecimento(estabelecimento);

        return getOutputEstabelecimento(estabelecimentoDb);
    }

    private static @NonNull OutputEstabelecimento getOutputEstabelecimento(Estabelecimento estabelecimentoDb) {
        Endereco enderecoDb = estabelecimentoDb.getEndereco();

        OutputEndereco outputEndereco = new OutputEndereco(enderecoDb.getId(), enderecoDb.getLogradouro(), enderecoDb.getNumero(), enderecoDb.getComplemento(), enderecoDb.getBairro(), enderecoDb.getCidade(), enderecoDb.getEstado(), enderecoDb.getCep());

        return new OutputEstabelecimento(estabelecimentoDb.getId(), estabelecimentoDb.getNome(), estabelecimentoDb.getHorarioAbertura(), estabelecimentoDb.getHorarioFechamento(), outputEndereco);
    }


    private static @NonNull Estabelecimento toEntity(InputEstabelecimento inputEstabelecimento) {
        InputEndereco inputEndereco = inputEstabelecimento.endereco();

        Endereco endereco = Endereco.create(inputEndereco.logradouro(), inputEndereco.numero(), inputEndereco.complemento(), inputEndereco.bairro(), inputEndereco.cidade(), inputEndereco.estado(), inputEndereco.cep());

        return Estabelecimento.create(inputEstabelecimento.nome(), inputEstabelecimento.horarioAbertura(), inputEstabelecimento.horarioFechamento(), endereco);
    }
}
