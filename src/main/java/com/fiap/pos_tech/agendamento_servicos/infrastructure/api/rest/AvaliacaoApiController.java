package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.AdicionarAvaliacaoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.InputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAvaliacao.dto.OutputAdicionarAvaliacao;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.AvaliacaoJson;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.NovaAvaliacaoJson;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AvaliacaoApiController implements AvaliacaoApi {

    private final AdicionarAvaliacaoUseCase adicionarAvaliacaoUseCase;

    @Override
    public ResponseEntity<AvaliacaoJson> addAvaliacao(NovaAvaliacaoJson body) {
        InputAdicionarAvaliacao inputAdicionarAvaliacao = new InputAdicionarAvaliacao(body.getNota(), UUID.fromString(body.getIdAgendamento()), body.getComentario(), LocalDateTime.now());

        OutputAdicionarAvaliacao outputAdicionarAvaliacao = adicionarAvaliacaoUseCase.execute(inputAdicionarAvaliacao);

        AvaliacaoJson avaliacaoJson = getAvaliacaoJson(outputAdicionarAvaliacao);

        return new ResponseEntity<AvaliacaoJson>(avaliacaoJson, HttpStatus.CREATED);
    }

    private static @NonNull AvaliacaoJson getAvaliacaoJson(OutputAdicionarAvaliacao outputAdicionarAvaliacao) {
        AvaliacaoJson avaliacaoJson = new AvaliacaoJson();
        avaliacaoJson.setId(outputAdicionarAvaliacao.id().toString());
        avaliacaoJson.setNota(outputAdicionarAvaliacao.nota());
        avaliacaoJson.setComentario(outputAdicionarAvaliacao.comentario());
        avaliacaoJson.setIdAgendamento(outputAdicionarAvaliacao.idAgendamento().toString());
        avaliacaoJson.dataAvaliacao(outputAdicionarAvaliacao.dataAvaliacao().toString());
        return avaliacaoJson;
    }
}
