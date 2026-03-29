package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.*;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.InputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.OutputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.AdicionarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.*;
import org.jspecify.annotations.NonNull;

public class AdicionarAgendamentoUseCase {

    private final IAgendamentoGateway adicionarAgendamentoGateway;
    private final IEstabelecimentoGateway estabelecimentoGateway;
    private final IProfissionalGateway profissionalGateway;
    private final IServicoGateway servicoGateway;
    private final IClienteGateway clienteGateway;
    private final AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain;

    private AdicionarAgendamentoUseCase(IAgendamentoGateway adicionarAgendamentoGateway,
                                        AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain,
                                        IEstabelecimentoGateway estabelecimentoGateway,
                                        IProfissionalGateway profissionalGateway,
                                        IServicoGateway servicoGateway,
                                        IClienteGateway clienteGateway
    ) {
        this.adicionarAgendamentoGateway = adicionarAgendamentoGateway;
        this.adicionarAgendamentoValidationChain = adicionarAgendamentoValidationChain;
        this.estabelecimentoGateway = estabelecimentoGateway;
        this.profissionalGateway = profissionalGateway;
        this.servicoGateway = servicoGateway;
        this.clienteGateway = clienteGateway;
    }

    public static AdicionarAgendamentoUseCase create(IAgendamentoGateway adicionarAgendamentoGateway,
                                                     AdicionarAgendamentoValidationChain adicionarAgendamentoValidationChain,
                                                     IEstabelecimentoGateway estabelecimentoGateway,
                                                     IProfissionalGateway profissionalGateway,
                                                     IServicoGateway servicoGateway,
                                                     IClienteGateway clienteGateway
    ) {
        return new AdicionarAgendamentoUseCase(adicionarAgendamentoGateway, adicionarAgendamentoValidationChain, estabelecimentoGateway, profissionalGateway, servicoGateway, clienteGateway);
    }

    public OutputAdicionarAgendamento execute(InputAdicionarAgendamento input) {
        Agendamento agendamento = toInput(input);

        adicionarAgendamentoValidationChain.validate(agendamento);

        Agendamento agendamentoDb = adicionarAgendamentoGateway.criarAgendamento(agendamento);

        adicionarAgendamentoGateway.enviarNotificacaoAgendamento(agendamentoDb);

        return getOutputAdicionarAgendamento(agendamentoDb);
    }

    private @NonNull Agendamento toInput(InputAdicionarAgendamento input) {
        Estabelecimento estabelecimento = estabelecimentoGateway.buscarEstabelecimento(input.idEstabelecimento());
        Profissional profissional = profissionalGateway.buscarProfissional(input.idProfissional());
        ServicoOferecido servicoOferecido = servicoGateway.buscarServicoOferecido(input.idServico());
        Cliente cliente =  clienteGateway.buscarCliente(input.idCliente());

        return Agendamento.create(input.horario(), servicoOferecido, estabelecimento, profissional, StatusAgendamentoEnum.AGENDADO, cliente);
    }

    private static @NonNull OutputAdicionarAgendamento getOutputAdicionarAgendamento(Agendamento agendamentoDb) {
        return new OutputAdicionarAgendamento(
                agendamentoDb.getId(),
                agendamentoDb.getHorario(),
                agendamentoDb.getServicoOferecido().getId(),
                agendamentoDb.getEstabelecimento().getId(),
                agendamentoDb.getProfissional().getId(),
                agendamentoDb.getCliente().getId()
        );
    }
}
