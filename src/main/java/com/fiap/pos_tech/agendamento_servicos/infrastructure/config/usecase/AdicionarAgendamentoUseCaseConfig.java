package com.fiap.pos_tech.agendamento_servicos.infrastructure.config.usecase;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.*;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.AdicionarAgendamentoUseCase;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdicionarAgendamentoUseCaseConfig {

    @Bean
    public AgendamentoExistenteHandler agendamentoExistenteHandler(IAgendamentoGateway agendamentoGateway) {
        return new AgendamentoExistenteHandler(agendamentoGateway);
    }

    @Bean
    public ProfissionalNaoExisteHandler profissionalNaoExisteHandler(IProfissionalGateway profissionalGateway) {
        return new ProfissionalNaoExisteHandler(profissionalGateway);
    }

    @Bean
    public EstabelecimentoNaoExisteHandler estabelecimentoNaoExisteHandler(IEstabelecimentoGateway estabelecimentoGateway) {
        return new EstabelecimentoNaoExisteHandler(estabelecimentoGateway);
    }

    @Bean
    public AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain(List<IAdicionarAgendamentoValidation> adicionarAgendamentoValidation) {
        return new AdicionarAgendamentoValidationChain(adicionarAgendamentoValidation);
    }

    @Bean
    public AdicionarAgendamentoUseCase adicionarAgendamentoUseCase(
            IAgendamentoGateway agendamentoGateway,
            AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain,
            IEstabelecimentoGateway estabelecimentoGateway,
            IProfissionalGateway profissionalGateway,
            IServicoGateway servicoGateway,
            IClienteGateway clienteGateway
    ){
        return AdicionarAgendamentoUseCase.create(agendamentoGateway, adicionarAgendamentoValidationChain, estabelecimentoGateway, profissionalGateway, servicoGateway, clienteGateway);
    }

}
