package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.graphql;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.BuscarEstabelecimentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto.InputBuscarEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto.InputBuscarEstabelecimentoEndereco;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto.InputBuscarEstabelecimentoServico;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto.OutputBuscarEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.EnderecoDTO;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.EstabelecimentoDTOInput;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.EstabelecimentoDTOOutput;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.HorarioDisponivelDTOOutput;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.ProfissionalDTOOutput;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.ServicoDTO;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.ServicoDTOOutput;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class EstabelecimentoGraphQLController {

    private final BuscarEstabelecimentoUseCase buscarEstabelecimentoUseCase;

    @QueryMapping
    public List<EstabelecimentoDTOOutput> buscarEstabelecimentos(@Argument EstabelecimentoDTOInput estabelecimentoDTO) {

        InputBuscarEstabelecimento estabelecimento = toInputBuscarEstabelecimento(estabelecimentoDTO);

        OutputBuscarEstabelecimento execute = buscarEstabelecimentoUseCase.execute(estabelecimento);

        return toEstabelecimentoDTOS(execute);
    }

    private static @NonNull List<EstabelecimentoDTOOutput> toEstabelecimentoDTOS(OutputBuscarEstabelecimento execute) {
        return execute.estabelecimentos()
                .stream()
                .map(estabelecimentoInput -> {
                    var endereco = estabelecimentoInput.endereco();
                    EnderecoDTO enderecoDTO = endereco == null
                            ? null
                            : new EnderecoDTO(
                            endereco.id() == null ? null : endereco.id().toString(),
                            endereco.logradouro(),
                            endereco.numero(),
                            endereco.complemento(),
                            endereco.bairro(),
                            endereco.cidade(),
                            endereco.estado(),
                            endereco.cep()
                    );

                    return new EstabelecimentoDTOOutput(
                            estabelecimentoInput.id().toString(),
                            estabelecimentoInput.nome(),
                            estabelecimentoInput.horarioAbertura().toString(),
                            estabelecimentoInput.horarioFechamento().toString(),
                            enderecoDTO,
                            estabelecimentoInput.nota() != null ? estabelecimentoInput.nota().floatValue() : null,
                            estabelecimentoInput.profissionais().stream()
                                    .map(profissional -> new ProfissionalDTOOutput(
                                            profissional.id().toString(),
                                            profissional.nome(),
                                            profissional.horariosDisponiveis().stream()
                                                    .map(horario -> new HorarioDisponivelDTOOutput(
                                                            horario.horario().toString(),
                                                            profissional.id().toString()
                                                    ))
                                                    .toList(),
                                            profissional.servicosOferecidos().stream()
                                                    .map(servicoOferecido -> new ServicoDTOOutput(
                                                            servicoOferecido.id().toString(),
                                                            servicoOferecido.nome(),
                                                            servicoOferecido.valor() == null ? null : servicoOferecido.valor().floatValue()
                                                    ))
                                                    .toList()
                                    ))
                                    .toList()
                    );
                })
                .toList();
    }

    private static @NonNull InputBuscarEstabelecimento toInputBuscarEstabelecimento(EstabelecimentoDTOInput estabelecimentoDTO) {
        if (estabelecimentoDTO == null) {
            return new InputBuscarEstabelecimento(null, null, null, null, null, null, null, null, null);
        }

        EnderecoDTO endereco = estabelecimentoDTO.endereco();
        ServicoDTO servico = estabelecimentoDTO.servico();

        InputBuscarEstabelecimentoEndereco estabelecimentoEndereco = endereco == null
                ? null
                : new InputBuscarEstabelecimentoEndereco(
                        parseUuid(endereco.id()),
                        endereco.logradouro(),
                        endereco.numero(),
                        endereco.complemento(),
                        endereco.bairro(),
                        endereco.cidade(),
                        endereco.estado(),
                        endereco.cep()
                );

        InputBuscarEstabelecimentoServico estabelecimentoServico = servico == null
                ? null
                : new InputBuscarEstabelecimentoServico(
                        servico.nome(),
                        servico.precoMaiorQue(),
                        servico.precoMenorQue()
                );

        return new InputBuscarEstabelecimento(
                parseUuid(estabelecimentoDTO.id()),
                estabelecimentoDTO.nome(),
                parseLocalTime(estabelecimentoDTO.horarioAbertura()),
                parseLocalTime(estabelecimentoDTO.horarioFechamento()),
                estabelecimentoEndereco,
                estabelecimentoDTO.notaMaiorQue(),
                estabelecimentoDTO.notaMenorQue(),
                estabelecimentoServico,
                parseLocalTime(estabelecimentoDTO.horarioAgendamento())
        );
    }

    private static UUID parseUuid(String value) {
        return value == null || value.isBlank() ? null : UUID.fromString(value);
    }

    private static LocalTime parseLocalTime(String value) {
        return value == null || value.isBlank() ? null : LocalTime.parse(value);
    }

}
