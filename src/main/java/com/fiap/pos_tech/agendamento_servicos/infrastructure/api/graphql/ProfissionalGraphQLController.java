package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.graphql;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.VerificarDisponibilidadeUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.InputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.OutputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.domain.model.HorarioDisponivel;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto.HorarioDisponivelDTOOutput;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ProfissionalGraphQLController {

    private final VerificarDisponibilidadeUseCase verificarDisponibilidade;

    @QueryMapping
    public List<HorarioDisponivelDTOOutput> disponibilidadeProfissional(
            @Argument("profissionalId")String profissionalID,
            @Argument("data") String data
    ) {

        InputVerificarDisponibilidade inputVerificarDisponibilidade = new InputVerificarDisponibilidade(
                UUID.fromString(profissionalID),
                LocalDate.parse(data)
        );

        OutputVerificarDisponibilidade execute = verificarDisponibilidade.execute(inputVerificarDisponibilidade);

        List<HorarioDisponivelDTOOutput> horarios = execute.horariosDisponiveis().stream().map(horarioDisponivel -> new HorarioDisponivelDTOOutput(
                horarioDisponivel.getHorario().toString(),
                horarioDisponivel.getProfissional().getId().toString()
        )).toList();

        return horarios;
    }
}
