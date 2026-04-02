package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.*;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.AdicionarProfissionalValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.*;
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
        List<OutputHorarioDisponivel> outputHorariosDisponiveis = profissionalDb.getHorarioDisponivel().stream().map(horarioDisponivel -> new OutputHorarioDisponivel(horarioDisponivel.getId(), horarioDisponivel.getHorario())).toList();

        return new OutputAdicionarProfissional(profissionalDb.getId(), profissionalDb.getNome(), profissionalDb.getEmail(), outputEspecialidades, outputServicoOferecidos, profissionalDb.getEstabelecimento().getId(), outputHorariosDisponiveis);
    }

    private @NonNull Profissional toEntity(InputAdicionarProfissional input) {
        Estabelecimento estabelecimento = estabelecimentoGateway.buscarEstabelecimento(input.idEstabelecimento());

        List<Especialidade> especialidades = input.especialidades().stream().map(inputEspecialidade -> Especialidade.create(inputEspecialidade.nome())).toList();
        List<ServicoOferecido> servicoOferecidos = input.servicoOferecidos().stream().map(inputServicoOferecido -> ServicoOferecido.create(inputServicoOferecido.nome(), inputServicoOferecido.valor().doubleValue())).toList();
        List<HorarioDisponivel> horariosDisponiveis = input.horariosDisponiveis().stream().map(inputHorarioDisponivel -> HorarioDisponivel.create(inputHorarioDisponivel.horario())).toList();

        return Profissional.create(input.nome(), input.email(), especialidades, servicoOferecidos, estabelecimento, horariosDisponiveis);
    }
}
