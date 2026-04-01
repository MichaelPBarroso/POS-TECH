package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.AdicionarAgendamentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.InputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.OutputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.CancelarAgendamentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.cancelarAgendamento.dto.InputCancelarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.ReagendarAgendamenoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto.InputReagendarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.reagendarAgendamento.dto.OutputReagendarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.RegistrarAusenciaUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.registarAusencia.dto.InputRegistarAusencia;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.AgendamentoJson;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.NovoAgendamentoJson;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.ReagendamentoJson;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class AgendamentoApiController implements AgendamentoApi {

    private final AdicionarAgendamentoUseCase adicionarAgendamentoUseCase;
    private final CancelarAgendamentoUseCase cancelarAgendamentoUseCase;
    private final ReagendarAgendamenoUseCase reagendarAgendamenoUseCase;
    private final RegistrarAusenciaUseCase registrarAusenciaUseCase;

    @Override
    public ResponseEntity<AgendamentoJson> addAgendamento(NovoAgendamentoJson body) {

        InputAdicionarAgendamento inputAdicionarAgendamento = new InputAdicionarAgendamento(LocalDate.parse(body.getData()), LocalTime.parse(body.getHorario()), UUID.fromString(body.getIdServico()), UUID.fromString(body.getIdEstabelecimento()), UUID.fromString(body.getIdProfissional()), UUID.fromString(body.getIdCliente()));

        OutputAdicionarAgendamento outputAdicionarAgendamento = adicionarAgendamentoUseCase.execute(inputAdicionarAgendamento);

        AgendamentoJson agendamentoJson = getAgendamentoJson(outputAdicionarAgendamento);

        return new ResponseEntity<AgendamentoJson>(agendamentoJson, HttpStatus.CREATED);
    }

    private static @NonNull AgendamentoJson getAgendamentoJson(OutputAdicionarAgendamento outputAdicionarAgendamento) {
        AgendamentoJson agendamentoJson = new AgendamentoJson();

        agendamentoJson.setId(outputAdicionarAgendamento.id().toString());
        agendamentoJson.setData(outputAdicionarAgendamento.data().toString());
        agendamentoJson.setHorario(outputAdicionarAgendamento.horario().toString());
        agendamentoJson.setIdServico(outputAdicionarAgendamento.idServico().toString());
        agendamentoJson.setIdEstabelecimento(outputAdicionarAgendamento.idEstabelecimento().toString());
        agendamentoJson.setIdProfissional(outputAdicionarAgendamento.idProfissional().toString());
        agendamentoJson.setIdCliente(outputAdicionarAgendamento.idCliente().toString());

        agendamentoJson.setStatusAgendamento(
                AgendamentoJson.StatusAgendamentoEnum.valueOf(outputAdicionarAgendamento.statusAgendamento().name())
        );

        return agendamentoJson;
    }

    @Override
    public ResponseEntity<String> ausenteAgendamento(String id) {

        InputRegistarAusencia inputRegistarAusencia = new InputRegistarAusencia(id);

        registrarAusenciaUseCase.execute(inputRegistarAusencia);

        return ResponseEntity.ok("Registro de ausencia realizado com sucesso.");
    }

    @Override
    public ResponseEntity<String> cancelarAgendamento(String id) {

        InputCancelarAgendamento inputCancelarAgendamento = new InputCancelarAgendamento(UUID.fromString(id));

        cancelarAgendamentoUseCase.execute(inputCancelarAgendamento);

        return ResponseEntity.ok("Cancelamento realizado com sucesso.");
    }

    @Override
    public ResponseEntity<AgendamentoJson> reagendarAgendamento(String id, ReagendamentoJson body) {

        InputReagendarAgendamento inputReagendarAgendamento = new InputReagendarAgendamento(UUID.fromString(id), LocalDate.parse(body.getData()), LocalTime.parse(body.getHorario()));

        OutputReagendarAgendamento outputReagendarAgendamento = reagendarAgendamenoUseCase.execute(inputReagendarAgendamento);

        return ResponseEntity.ok(getAgendamentoJson(outputReagendarAgendamento));
    }


    private static @NonNull AgendamentoJson getAgendamentoJson(OutputReagendarAgendamento outputReagendarAgendamento) {
        AgendamentoJson agendamentoJson = new AgendamentoJson();

        agendamentoJson.setId(outputReagendarAgendamento.id().toString());
        agendamentoJson.setData(outputReagendarAgendamento.data().toString());
        agendamentoJson.setHorario(outputReagendarAgendamento.horario().toString());
        agendamentoJson.setIdServico(outputReagendarAgendamento.idServico().toString());
        agendamentoJson.setIdEstabelecimento(outputReagendarAgendamento.idEstabelecimento().toString());
        agendamentoJson.setIdProfissional(outputReagendarAgendamento.idProfissional().toString());
        agendamentoJson.setIdCliente(outputReagendarAgendamento.idCliente().toString());

        agendamentoJson.setStatusAgendamento(
                AgendamentoJson.StatusAgendamentoEnum.valueOf(outputReagendarAgendamento.statusAgendamento().name())
        );

        return agendamentoJson;
    }

}
