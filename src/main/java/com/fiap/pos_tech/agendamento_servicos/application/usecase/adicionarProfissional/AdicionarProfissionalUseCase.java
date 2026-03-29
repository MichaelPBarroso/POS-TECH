package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.InputAdicionarProfissional;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.OutputAdicionarProfissional;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.OutputEspecialidade;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.OutputServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.AdicionarProfissionalValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Especialidade;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class AdicionarProfissionalUseCase {

    private final IEstabelecimentoGateway estabelecimentoGateway;
    private final IProfissionalGateway adicionarProfissionalGateway;
    private final AdicionarProfissionalValidationChain adicionarProfissionalValidationChain;

    private AdicionarProfissionalUseCase(IProfissionalGateway adicionarProfissionalGateway, AdicionarProfissionalValidationChain adicionarProfissionalValidationChain, IEstabelecimentoGateway estabelecimentoGateway) {
        this.adicionarProfissionalGateway = adicionarProfissionalGateway;
        this.adicionarProfissionalValidationChain = adicionarProfissionalValidationChain;
        this.estabelecimentoGateway = estabelecimentoGateway;
    }

    public static AdicionarProfissionalUseCase create(IProfissionalGateway adicionarProfissionalGateway, AdicionarProfissionalValidationChain adicionarProfissionalValidationChain, IEstabelecimentoGateway estabelecimentoGateway) {
        return new AdicionarProfissionalUseCase(adicionarProfissionalGateway, adicionarProfissionalValidationChain, estabelecimentoGateway);
    }

    public OutputAdicionarProfissional execute(InputAdicionarProfissional input) {
        Profissional profissional = toEntity(input);

        adicionarProfissionalValidationChain.validate(profissional);

        Profissional profissionalDb = adicionarProfissionalGateway.criarProfissional(profissional);

        return getOutputAdicionarProfissional(profissionalDb, profissional);
    }

    private static @NonNull OutputAdicionarProfissional getOutputAdicionarProfissional(Profissional profissionalDb, Profissional profissional) {
        List<OutputEspecialidade> outputEspecialidades = profissionalDb.getEspecialidades().stream().map(especialidade -> new OutputEspecialidade(especialidade.getId(), especialidade.getNome())).toList();
        List<OutputServicoOferecido> outputServicoOferecidos = profissionalDb.getServicoOferecidos().stream().map(servicoOferecido -> new OutputServicoOferecido(servicoOferecido.getId(), servicoOferecido.getNome(), BigDecimal.valueOf(servicoOferecido.getValor()))).toList();

        return new OutputAdicionarProfissional(profissionalDb.getId(), profissional.getNome(), outputEspecialidades, outputServicoOferecidos, profissionalDb.getEstabelecimento().getId() );
    }

    private @NonNull Profissional toEntity(InputAdicionarProfissional input) {
        Estabelecimento estabelecimento = estabelecimentoGateway.buscarEstabelecimento(input.idEstabelecimento());

        List<Especialidade> especialidades = input.especialidades().stream().map(inputEspecialidade -> Especialidade.create(inputEspecialidade.nome())).toList();
        List<ServicoOferecido> servicoOferecidos = input.servicoOferecidos().stream().map(inputServicoOferecido -> ServicoOferecido.create(inputServicoOferecido.nome(), inputServicoOferecido.valor().doubleValue())).toList();

        return Profissional.create(input.nome(), especialidades, servicoOferecidos, estabelecimento);
    }
}
