package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto.*;
import com.fiap.pos_tech.agendamento_servicos.domain.model.*;
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
        FiltroAvancado filtroAvancado = toFiltroAvancado(input);

        List<Estabelecimento> estabelecimentos = estabelecimentoGateway.buscarEstabelecimentos(filtroAvancado);

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
                    estabelecimentoDb.getNotaMedia(),
                    toOutputBuscarEstabelecimentoProfissionais(estabelecimentoDb.getProfissionais()),
                    toOutputBuscarEstabelecimentoFotos(estabelecimentoDb.getFotos())
            );
        }).toList();
        return list;
    }

    private static @NonNull List<OutputBuscarEstabelecimentoProfissional> toOutputBuscarEstabelecimentoProfissionais(List<Profissional> profissionais) {
        if (profissionais == null) {
            return List.of();
        }

        return profissionais.stream()
                .map(profissional -> new OutputBuscarEstabelecimentoProfissional(
                        profissional.getId(),
                        profissional.getNome(),
                        toOutputBuscarEstabelecimentoHorarios(profissional.getHorarioDisponivel()),
                        toOutputBuscarEstabelecimentoServicos(profissional.getServicoOferecidos())
                ))
                .toList();
    }

    private static @NonNull List<OutputBuscarEstabelecimentoHorarioDisponivel> toOutputBuscarEstabelecimentoHorarios(List<HorarioDisponivel> horariosDisponiveis) {
        if (horariosDisponiveis == null) {
            return List.of();
        }

        return horariosDisponiveis.stream()
                .map(horario -> new OutputBuscarEstabelecimentoHorarioDisponivel(
                        horario.getId(),
                        horario.getHorario()
                ))
                .toList();
    }

    private static @NonNull List<OutputBuscarEstabelecimentoServicoOferecido> toOutputBuscarEstabelecimentoServicos(List<ServicoOferecido> servicosOferecidos) {
        if (servicosOferecidos == null) {
            return List.of();
        }

        return servicosOferecidos.stream()
                .map(servico -> new OutputBuscarEstabelecimentoServicoOferecido(
                        servico.getId(),
                        servico.getNome(),
                        servico.getValor() == null ? null : BigDecimal.valueOf(servico.getValor())
                ))
                .toList();
    }
    private static @NonNull List<OutputBuscarEstabelecimentoFotos> toOutputBuscarEstabelecimentoFotos(List<FotoEstabelecimento> fotoEstabelecimentos) {
        if (fotoEstabelecimentos == null) {
            return List.of();
        }

        return fotoEstabelecimentos.stream()
                .map(servico -> new OutputBuscarEstabelecimentoFotos(
                        servico.getId(),
                        servico.getUrl()
                ))
                .toList();
    }

    private static @NonNull FiltroAvancado toFiltroAvancado(InputBuscarEstabelecimento input) {
        if (input == null) {
            return FiltroAvancado.create(null, null, null, null, null, null, null, null, null, null, null, null);
        }

        InputBuscarEstabelecimentoEndereco inputEndereco = input.endereco();
        InputBuscarEstabelecimentoServico inputServico = input.servico();

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

        ServicoOferecido servicoOferecido = inputServico == null
                ? null
                : ServicoOferecido.create(null, inputServico.nome(), null);

        return FiltroAvancado.create(
                input.id(),
                input.nome(),
                input.horarioAbertura(),
                input.horarioFechamento(),
                input.notaMaiorQue(),
                input.notaMenorQue(),
                endereco,
                null,
                servicoOferecido,
                input.horarioAgendamento(),
                inputServico == null || inputServico.precoMaiorQue() == null ? null : BigDecimal.valueOf(inputServico.precoMaiorQue()),
                inputServico == null || inputServico.precoMenorQue() == null ? null : BigDecimal.valueOf(inputServico.precoMenorQue())
        );
    }
}
