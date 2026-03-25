package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.InputAdicionarProfissional;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.OutputAdicionarProfissional;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.AdicionarProfissionalValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

public class AdicionarProfissionalUseCase {

    private final IProfissionalGateway adicionarProfissionalGateway;
    private final AdicionarProfissionalValidationChain adicionarProfissionalValidationChain;

    private AdicionarProfissionalUseCase(IProfissionalGateway adicionarProfissionalGateway, AdicionarProfissionalValidationChain adicionarProfissionalValidationChain) {
        this.adicionarProfissionalGateway = adicionarProfissionalGateway;
        this.adicionarProfissionalValidationChain = adicionarProfissionalValidationChain;
    }

    public static AdicionarProfissionalUseCase create(IProfissionalGateway adicionarProfissionalGateway, AdicionarProfissionalValidationChain adicionarProfissionalValidationChain) {
        return new AdicionarProfissionalUseCase(adicionarProfissionalGateway, adicionarProfissionalValidationChain);
    }

    public OutputAdicionarProfissional execute(InputAdicionarProfissional input) {
        Profissional profissional = input.toEntity();

        adicionarProfissionalValidationChain.validate(profissional);

        adicionarProfissionalGateway.criarProfissional(profissional);

        return new OutputAdicionarProfissional();
    }
}
