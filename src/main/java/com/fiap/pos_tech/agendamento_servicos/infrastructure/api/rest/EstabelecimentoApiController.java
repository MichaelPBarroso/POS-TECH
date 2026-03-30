package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.AdicionarEstabelecimentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.InputEndereco;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.InputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.OutputEndereco;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.OutputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.EnderecoJson;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.EstabelecimentoJson;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.NovoEnderecoJson;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.api.rest.json.NovoEstabelecimentoJson;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class EstabelecimentoApiController implements EstabelecimentoApi {

    private final AdicionarEstabelecimentoUseCase adicionarEstabelecimentoUseCase;

    @Override
    public ResponseEntity<EstabelecimentoJson> addEstabelecimento(NovoEstabelecimentoJson body) {

        InputEstabelecimento inputEstabelecimento = toInput(body);

        OutputEstabelecimento outputEstabelecimento = adicionarEstabelecimentoUseCase.execute(inputEstabelecimento);

        EstabelecimentoJson estabelecimentoJson = getEstabelecimentoJson(outputEstabelecimento);

        return new ResponseEntity<>(estabelecimentoJson, HttpStatus.CREATED);
    }

    private static @NonNull EstabelecimentoJson getEstabelecimentoJson(OutputEstabelecimento outputEstabelecimento) {
        OutputEndereco endereco = outputEstabelecimento.endereco();

        EnderecoJson enderecoJson = new EnderecoJson();
        enderecoJson.setId(endereco.id().toString());
        enderecoJson.setLogradouro(endereco.logradouro());
        enderecoJson.setNumero(endereco.numero());
        enderecoJson.setComplemento(endereco.complemento());
        enderecoJson.setBairro(endereco.bairro());
        enderecoJson.setCidade(endereco.cidade());
        enderecoJson.setEstado(endereco.estado());
        enderecoJson.setCep(endereco.cep());

        EstabelecimentoJson estabelecimentoJson = new EstabelecimentoJson();

        estabelecimentoJson.setId(outputEstabelecimento.id().toString());
        estabelecimentoJson.setHorarioAbertura(outputEstabelecimento.horarioAbertura().toString());
        estabelecimentoJson.setHorarioFechamento(outputEstabelecimento.horarioFechamento().toString());
        estabelecimentoJson.setEndereco(enderecoJson);
        estabelecimentoJson.setNome(outputEstabelecimento.nome());

        return estabelecimentoJson;
    }

    private static @NonNull InputEstabelecimento toInput(NovoEstabelecimentoJson body) {
        NovoEnderecoJson bodyEndereco = body.getEndereco();

        InputEndereco inputEndereco = new InputEndereco(bodyEndereco.getLogradouro(), bodyEndereco.getNumero(), bodyEndereco.getComplemento(), bodyEndereco.getBairro(), bodyEndereco.getCidade(), bodyEndereco.getEstado(), bodyEndereco.getCep());

        return new InputEstabelecimento(body.getNome(), LocalTime.parse(body.getHorarioAbertura()), LocalTime.parse(body.getHorarioFechamento()), inputEndereco);
    }
}
