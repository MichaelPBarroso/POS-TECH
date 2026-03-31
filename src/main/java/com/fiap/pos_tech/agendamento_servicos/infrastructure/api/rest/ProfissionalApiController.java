package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.AdicionarProfissionalUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.*;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.*;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProfissionalApiController implements ProfissionalApi {

    private final AdicionarProfissionalUseCase adicionarProfissionalUseCase;

    @Override
    public ResponseEntity<ProfissionalJson> addProfissional(NovoProfissionalJson body) {

        InputAdicionarProfissional inputAdicionarProfissional = toInput(body);

        OutputAdicionarProfissional output = adicionarProfissionalUseCase.execute(inputAdicionarProfissional);

        ProfissionalJson profissionalJson = getProfissionalJson(output);

        return new ResponseEntity<>(profissionalJson, HttpStatus.CREATED);
    }

    private static @NonNull ProfissionalJson getProfissionalJson(OutputAdicionarProfissional output) {
        ProfissionalJson profissionalJson = new ProfissionalJson();

        profissionalJson.setId(output.id().toString());
        profissionalJson.setNome(output.nome());
        profissionalJson.setIdEstabelecimento(output.idEstabelecimento().toString());

        List<EspecialidadeJson> especialidadesJson = output.especialidades().stream().map(especialidade -> {
            EspecialidadeJson especialidadeJson = new EspecialidadeJson();

            especialidadeJson.setId(especialidade.id().toString());
            especialidadeJson.setNome(especialidade.nome());

            return especialidadeJson;
        }).toList();


        profissionalJson.setEspecialidade(especialidadesJson);

        List<ServicoJson> servicosJson = output.servicoOferecidos().stream().map(servico -> {
            ServicoJson servicoJson = new ServicoJson();

            servicoJson.setId(servico.id().toString());
            servicoJson.setNome(servico.nome());
            servicoJson.setValor(servico.valor());

            return servicoJson;
        }).toList();

        profissionalJson.setServico(servicosJson);

        List<HorarioDisponivelJson> horariosDisponiveisJson = output.horariosDisponiveis().stream().map(horario -> {
            HorarioDisponivelJson horarioDisponivelJson = new HorarioDisponivelJson();

            horarioDisponivelJson.setId(horario.id().toString());
            horarioDisponivelJson.setHorario(horario.horario().toString());

            return horarioDisponivelJson;
        }).toList();

        profissionalJson.setHorario(horariosDisponiveisJson);

        return profissionalJson;
    }

    private static @NonNull InputAdicionarProfissional toInput(NovoProfissionalJson body) {
        List<InputEspecialidade> especialidades = body.getEspecialidade().stream().map(especialidade -> new InputEspecialidade(especialidade.getNome())).toList();
        List<InputServicoOferecido> servicoOferecidos = body.getServico().stream().map(servicos -> new InputServicoOferecido(servicos.getNome(), servicos.getValor())).toList();
        List<InputHorarioDisponivel> horariosDisponiveis = body.getHorario().stream().map(horario -> new InputHorarioDisponivel(LocalTime.parse(horario.getHorario()))).toList();

        return new InputAdicionarProfissional(body.getNome(), especialidades, servicoOferecidos, UUID.fromString(body.getIdEstabelecimento()), horariosDisponiveis);
    }
}
