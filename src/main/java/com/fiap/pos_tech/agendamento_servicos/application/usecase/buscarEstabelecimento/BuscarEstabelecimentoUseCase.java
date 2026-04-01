package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto.*;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
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
        if (estabelecimentos == null) {
            return List.of();
        }

        List<OutputBuscarEstabelecimentoEstabelecimento> list = estabelecimentos.stream().map(estabelecimentoDb -> {
            Endereco enderecoDb = estabelecimentoDb.getEndereco();

            OutputBuscarEstabelecimentoEndereco endereco = enderecoDb == null
                    ? null
                    : new OutputBuscarEstabelecimentoEndereco(
                    enderecoDb.getId(),
                    enderecoDb.getLogradouro(),
                    enderecoDb.getNumero(),
                    enderecoDb.getComplemento(),
                    enderecoDb.getBairro(),
                    enderecoDb.getCidade(),
                    enderecoDb.getEstado(),
                    enderecoDb.getCep()
            );

            return new OutputBuscarEstabelecimentoEstabelecimento(
                    estabelecimentoDb.getId(),
                    estabelecimentoDb.getNome(),
                    estabelecimentoDb.getHorarioAbertura(),
                    estabelecimentoDb.getHorarioFechamento(),
                    endereco,
                    estabelecimentoDb.getNotaMedia()
            );
        }).toList();
        return list;
    }

    private static @NonNull Estabelecimento toEntity(InputBuscarEstabelecimento input) {
        if (input == null) {
            return Estabelecimento.create(null, null, null, null, null, (BigDecimal) null);
        }

        InputBuscarEstabelecimentoEndereco inputEndereco = input.endereco();

        Endereco endereco = inputEndereco == null
                ? null
                : Endereco.create(
                inputEndereco.id(),
                inputEndereco.logradouro(),
                inputEndereco.numero(),
                inputEndereco.complemento(),
                inputEndereco.bairro(),
                inputEndereco.cidade(),
                inputEndereco.estado(),
                inputEndereco.cep()
        );

        return Estabelecimento.create(input.id(), input.nome(), input.horarioAbertura(), input.horarioFechamento(), endereco, (BigDecimal) null);
    }
}
