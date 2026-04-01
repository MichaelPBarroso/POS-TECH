package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.AgendamentoJaExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.exceptions.EstabelecimentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.exceptions.ProfissionalNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IClienteGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IServicoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.InputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.dto.OutputAdicionarAgendamento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.AdicionarAgendamentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.AgendamentoExistenteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.EstabelecimentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarAgendamento.validation.ProfissionalNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Cliente;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdicionarAgendamentoUseCaseTest {

    private static final UUID AGENDAMENTO_ID = UUID.randomUUID();
    private static final UUID SERVICO_ID = UUID.randomUUID();
    private static final UUID ESTABELECIMENTO_ID = UUID.randomUUID();
    private static final UUID PROFISSIONAL_ID = UUID.randomUUID();
    private static final UUID CLIENTE_ID = UUID.randomUUID();
    private static final UUID ENDERECO_ID = UUID.randomUUID();
    private static final LocalDate DATA = LocalDate.of(2026, 4, 10);
    private static final LocalTime HORARIO = LocalTime.of(14, 30);

    private final IAgendamentoGateway agendamentoGateway = mock(IAgendamentoGateway.class);
    private final IEstabelecimentoGateway estabelecimentoGateway = mock(IEstabelecimentoGateway.class);
    private final IProfissionalGateway profissionalGateway = mock(IProfissionalGateway.class);
    private final IServicoGateway servicoGateway = mock(IServicoGateway.class);
    private final IClienteGateway clienteGateway = mock(IClienteGateway.class);

    @DisplayName("Adicionar agendamento com sucesso")
    @Test
    void adicionarAgendamento() {
        Estabelecimento estabelecimento = criarEstabelecimento();
        Profissional profissional = criarProfissional(estabelecimento);
        ServicoOferecido servico = criarServico(profissional);
        Cliente cliente = criarCliente();
        Agendamento agendamentoPersistido = Agendamento.create(
                AGENDAMENTO_ID,
                DATA,
                HORARIO,
                servico,
                estabelecimento,
                profissional,
                StatusAgendamentoEnum.AGENDADO,
                cliente
        );

        InputAdicionarAgendamento input = new InputAdicionarAgendamento(
                DATA,
                HORARIO,
                SERVICO_ID,
                ESTABELECIMENTO_ID,
                PROFISSIONAL_ID,
                CLIENTE_ID
        );

        AdicionarAgendamentoValidationChain validationChain = mock(AdicionarAgendamentoValidationChain.class);

        when(estabelecimentoGateway.buscarEstabelecimento(ESTABELECIMENTO_ID)).thenReturn(estabelecimento);
        when(profissionalGateway.buscarProfissional(PROFISSIONAL_ID)).thenReturn(profissional);
        when(servicoGateway.buscarServicoOferecido(SERVICO_ID)).thenReturn(servico);
        when(clienteGateway.buscarCliente(CLIENTE_ID)).thenReturn(cliente);
        when(agendamentoGateway.criarAgendamento(any())).thenReturn(agendamentoPersistido);

        AdicionarAgendamentoUseCase useCase = AdicionarAgendamentoUseCase.create(
                agendamentoGateway,
                validationChain,
                estabelecimentoGateway,
                profissionalGateway,
                servicoGateway,
                clienteGateway
        );

        OutputAdicionarAgendamento output = useCase.execute(input);

        ArgumentCaptor<Agendamento> validationCaptor = ArgumentCaptor.forClass(Agendamento.class);
        ArgumentCaptor<Agendamento> gatewayCaptor = ArgumentCaptor.forClass(Agendamento.class);
        ArgumentCaptor<Agendamento> notificacaoCaptor = ArgumentCaptor.forClass(Agendamento.class);

        verify(validationChain, times(1)).validate(validationCaptor.capture());
        verify(agendamentoGateway, times(1)).criarAgendamento(gatewayCaptor.capture());
        verify(agendamentoGateway, times(1)).enviarNotificacaoAgendamento(notificacaoCaptor.capture());

        InOrder inOrder = inOrder(validationChain, agendamentoGateway);
        inOrder.verify(validationChain).validate(any(Agendamento.class));
        inOrder.verify(agendamentoGateway).criarAgendamento(any(Agendamento.class));
        inOrder.verify(agendamentoGateway).enviarNotificacaoAgendamento(any(Agendamento.class));

        Agendamento agendamentoValidado = validationCaptor.getValue();
        Agendamento agendamentoEnviado = gatewayCaptor.getValue();
        Agendamento agendamentoNotificado = notificacaoCaptor.getValue();

        assertNotNull(output);
        assertEquals(AGENDAMENTO_ID, output.id());
        assertEquals(DATA, output.data());
        assertEquals(HORARIO, output.horario());
        assertEquals(SERVICO_ID, output.idServico());
        assertEquals(ESTABELECIMENTO_ID, output.idEstabelecimento());
        assertEquals(PROFISSIONAL_ID, output.idProfissional());
        assertEquals(CLIENTE_ID, output.idCliente());
        assertEquals(StatusAgendamentoEnum.AGENDADO, output.statusAgendamento());

        assertEquals(DATA, agendamentoValidado.getData());
        assertEquals(HORARIO, agendamentoValidado.getHorario());
        assertEquals(SERVICO_ID, agendamentoValidado.getServicoOferecido().getId());
        assertEquals(ESTABELECIMENTO_ID, agendamentoValidado.getEstabelecimento().getId());
        assertEquals(PROFISSIONAL_ID, agendamentoValidado.getProfissional().getId());
        assertEquals(CLIENTE_ID, agendamentoValidado.getCliente().getId());
        assertEquals(StatusAgendamentoEnum.AGENDADO, agendamentoValidado.getStatusAgendamentoEnum());

        assertEquals(agendamentoValidado.getData(), agendamentoEnviado.getData());
        assertEquals(agendamentoValidado.getHorario(), agendamentoEnviado.getHorario());
        assertEquals(agendamentoValidado.getServicoOferecido().getId(), agendamentoEnviado.getServicoOferecido().getId());
        assertEquals(agendamentoValidado.getEstabelecimento().getId(), agendamentoEnviado.getEstabelecimento().getId());
        assertEquals(agendamentoValidado.getProfissional().getId(), agendamentoEnviado.getProfissional().getId());
        assertEquals(agendamentoValidado.getCliente().getId(), agendamentoEnviado.getCliente().getId());
        assertEquals(AGENDAMENTO_ID, agendamentoNotificado.getId());
    }

    @DisplayName("Deve lançar erro quando estabelecimento não existir")
    @Test
    void adicionarAgendamentoComEstabelecimentoInexistente() {
        InputAdicionarAgendamento input = criarInput();
        Profissional profissional = criarProfissional(criarEstabelecimento());
        ServicoOferecido servico = criarServico(profissional);
        Cliente cliente = criarCliente();

        when(estabelecimentoGateway.buscarEstabelecimento(ESTABELECIMENTO_ID)).thenReturn(null);
        when(profissionalGateway.buscarProfissional(PROFISSIONAL_ID)).thenReturn(profissional);
        when(servicoGateway.buscarServicoOferecido(SERVICO_ID)).thenReturn(servico);
        when(clienteGateway.buscarCliente(CLIENTE_ID)).thenReturn(cliente);

        AdicionarAgendamentoUseCase useCase = AdicionarAgendamentoUseCase.create(
                agendamentoGateway,
                criarValidationChainReal(),
                estabelecimentoGateway,
                profissionalGateway,
                servicoGateway,
                clienteGateway
        );

        assertThrows(EstabelecimentoNaoExisteException.class, () -> useCase.execute(input));

        verify(agendamentoGateway, never()).criarAgendamento(any());
        verify(agendamentoGateway, never()).enviarNotificacaoAgendamento(any());
    }

    @DisplayName("Deve lançar erro quando profissional não existir")
    @Test
    void adicionarAgendamentoComProfissionalInexistente() {
        InputAdicionarAgendamento input = criarInput();
        Estabelecimento estabelecimento = criarEstabelecimento();
        ServicoOferecido servico = criarServico(Profissional.create(PROFISSIONAL_ID, "Joao", estabelecimento));
        Cliente cliente = criarCliente();

        when(estabelecimentoGateway.buscarEstabelecimento(ESTABELECIMENTO_ID)).thenReturn(estabelecimento);
        when(profissionalGateway.buscarProfissional(PROFISSIONAL_ID)).thenReturn(null);
        when(servicoGateway.buscarServicoOferecido(SERVICO_ID)).thenReturn(servico);
        when(clienteGateway.buscarCliente(CLIENTE_ID)).thenReturn(cliente);

        AdicionarAgendamentoUseCase useCase = AdicionarAgendamentoUseCase.create(
                agendamentoGateway,
                criarValidationChainReal(),
                estabelecimentoGateway,
                profissionalGateway,
                servicoGateway,
                clienteGateway
        );

        assertThrows(ProfissionalNaoExisteException.class, () -> useCase.execute(input));

        verify(agendamentoGateway, never()).criarAgendamento(any());
        verify(agendamentoGateway, never()).enviarNotificacaoAgendamento(any());
    }

    @DisplayName("Deve lançar erro quando já existir agendamento para o mesmo horário")
    @Test
    void adicionarAgendamentoQuandoJaExiste() {
        InputAdicionarAgendamento input = criarInput();
        Estabelecimento estabelecimento = criarEstabelecimento();
        Profissional profissional = criarProfissional(estabelecimento);
        ServicoOferecido servico = criarServico(profissional);
        Cliente cliente = criarCliente();
        Agendamento agendamentoExistente = Agendamento.create(
                AGENDAMENTO_ID,
                DATA,
                HORARIO,
                servico,
                estabelecimento,
                profissional,
                StatusAgendamentoEnum.AGENDADO,
                cliente
        );

        when(estabelecimentoGateway.buscarEstabelecimento(ESTABELECIMENTO_ID)).thenReturn(estabelecimento);
        when(profissionalGateway.buscarProfissional(PROFISSIONAL_ID)).thenReturn(profissional);
        when(servicoGateway.buscarServicoOferecido(SERVICO_ID)).thenReturn(servico);
        when(clienteGateway.buscarCliente(CLIENTE_ID)).thenReturn(cliente);
        when(agendamentoGateway.buscarAgendamento(any(), any(), any(), any())).thenReturn(agendamentoExistente);

        AdicionarAgendamentoUseCase useCase = AdicionarAgendamentoUseCase.create(
                agendamentoGateway,
                new AdicionarAgendamentoValidationChain(List.of(new AgendamentoExistenteHandler(agendamentoGateway))),
                estabelecimentoGateway,
                profissionalGateway,
                servicoGateway,
                clienteGateway
        );

        assertThrows(AgendamentoJaExisteException.class, () -> useCase.execute(input));

        verify(agendamentoGateway, never()).criarAgendamento(any());
        verify(agendamentoGateway, never()).enviarNotificacaoAgendamento(any());
    }

    private AdicionarAgendamentoValidationChain criarValidationChainReal() {
        return new AdicionarAgendamentoValidationChain(List.of(
                new EstabelecimentoNaoExisteHandler(estabelecimentoGateway),
                new ProfissionalNaoExisteHandler(profissionalGateway)
        ));
    }

    private InputAdicionarAgendamento criarInput() {
        return new InputAdicionarAgendamento(
                DATA,
                HORARIO,
                SERVICO_ID,
                ESTABELECIMENTO_ID,
                PROFISSIONAL_ID,
                CLIENTE_ID
        );
    }

    private Estabelecimento criarEstabelecimento() {
        Endereco endereco = Endereco.create(
                ENDERECO_ID,
                "Rua Brigadeiro",
                "100",
                null,
                "Centro",
                "Sao Paulo",
                "SP",
                "00000-000"
        );

        return Estabelecimento.create(
                ESTABELECIMENTO_ID,
                "Barbearia Central",
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                endereco,
                (java.math.BigDecimal) null
        );
    }

    private Profissional criarProfissional(Estabelecimento estabelecimento) {
        return Profissional.create(
                PROFISSIONAL_ID,
                "Joao",
                estabelecimento
        );
    }

    private ServicoOferecido criarServico(Profissional profissional) {
        return ServicoOferecido.create(
                SERVICO_ID,
                "Corte",
                50.0,
                profissional
        );
    }

    private Cliente criarCliente() {
        return Cliente.create(
                CLIENTE_ID,
                "Maria",
                "12345678900",
                "maria@email.com"
        );
    }
}
