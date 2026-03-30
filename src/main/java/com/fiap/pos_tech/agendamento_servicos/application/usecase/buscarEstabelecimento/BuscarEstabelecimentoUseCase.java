package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto.*;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class BuscarEstabelecimentoUseCase {

    private final IEstabelecimentoGateway estabelecimentoGateway;

    private BuscarEstabelecimentoUseCase(IEstabelecimentoGateway estabelecimentoGateway){
        this.estabelecimentoGateway = estabelecimentoGateway;
    }

    public static BuscarEstabelecimentoUseCase create(IEstabelecimentoGateway estabelecimentoGateway){
        return new BuscarEstabelecimentoUseCase(estabelecimentoGateway);
    }

    public OutputBuscarEstabelecimento execute(InputBuscarEstabelecimento input){
        Estabelecimento estabelecimento = toEntity(input);

        List<Estabelecimento> estabelecimentos = estabelecimentoGateway.buscarEstabelecimentos(estabelecimento);

        List<OutputBuscarEstabelecimentoEstabelecimento> list = toOutputBuscarEstabelecimentoEstabelecimentos(estabelecimentos);

        return new OutputBuscarEstabelecimento(list);

    }

    private static @NonNull List<OutputBuscarEstabelecimentoEstabelecimento> toOutputBuscarEstabelecimentoEstabelecimentos(List<Estabelecimento> estabelecimentos) {
        List<OutputBuscarEstabelecimentoEstabelecimento> list = estabelecimentos.stream().map(estabelecimentoDb -> {
            OutputBuscarEstabelecimentoEndereco endereco = new OutputBuscarEstabelecimentoEndereco(
                    estabelecimentoDb.getEndereco().getId(),
                    estabelecimentoDb.getEndereco().getLogradouro(),
                    estabelecimentoDb.getEndereco().getNumero(),
                    estabelecimentoDb.getEndereco().getComplemento(),
                    estabelecimentoDb.getEndereco().getBairro(),
                    estabelecimentoDb.getEndereco().getCidade(),
                    estabelecimentoDb.getEndereco().getEstado(),
                    estabelecimentoDb.getEndereco().getCep()
            );

            return new OutputBuscarEstabelecimentoEstabelecimento(
                    estabelecimentoDb.getId(),
                    estabelecimentoDb.getNome(),
                    estabelecimentoDb.getHorarioAbertura(),
                    estabelecimentoDb.getHorarioFechamento(),
                    endereco
            );
        }).toList();
        return list;
    }

    private static @NonNull Estabelecimento toEntity(InputBuscarEstabelecimento input) {
        InputBuscarEstabelecimentoEndereco inputEndereco = input.endereco();

        Endereco endereco = Endereco.create(inputEndereco.id(), inputEndereco.logradouro(), inputEndereco.numero(), inputEndereco.complemento(), inputEndereco.bairro(), inputEndereco.cidade(), inputEndereco.estado(), inputEndereco.cep());

        return Estabelecimento.create(input.id(), input.nome(), input.horarioAbertura(), input.horarioFechamento(), endereco);
    }
}
