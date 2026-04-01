package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.EstabelecimentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.InputAdicionarProfissional;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.InputEspecialidade;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.InputHorarioDisponivel;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.InputServicoOferecido;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto.OutputAdicionarProfissional;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.AdicionarProfissionalValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.validation.EstabelecimentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Especialidade;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.HorarioDisponivel;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import com.fiap.pos_tech.agendamento_servicos.domain.model.ServicoOferecido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdicionarProfissionalUseCaseTest {

    private static final UUID ESTABELECIMENTO_ID = UUID.randomUUID();
    private static final UUID ENDERECO_ID = UUID.randomUUID();
    private static final UUID PROFISSIONAL_ID = UUID.randomUUID();
    private static final UUID ESPECIALIDADE_ID = UUID.randomUUID();
    private static final UUID SERVICO_ID = UUID.randomUUID();
    private static final UUID HORARIO_ID = UUID.randomUUID();
    private static final String NOME_PROFISSIONAL = "Joao";
    private static final String NOME_ESPECIALIDADE = "Barbeiro";
    private static final String NOME_SERVICO = "Corte";
    private static final BigDecimal VALOR_SERVICO = BigDecimal.valueOf(55.90);
    private static final LocalTime HORARIO_DISPONIVEL = LocalTime.of(9, 0);

    @DisplayName("Adicionar profissional com sucesso")
    @Test
    void adicionarProfissional() {
        InputAdicionarProfissional input = new InputAdicionarProfissional(
                NOME_PROFISSIONAL,
                List.of(new InputEspecialidade(NOME_ESPECIALIDADE)),
                List.of(new InputServicoOferecido(NOME_SERVICO, VALOR_SERVICO)),
                ESTABELECIMENTO_ID,
                List.of(new InputHorarioDisponivel(HORARIO_DISPONIVEL))
        );

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
        Estabelecimento estabelecimento = Estabelecimento.create(
                ESTABELECIMENTO_ID,
                "Barbearia Central",
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                endereco,
                (java.math.BigDecimal) null
        );
        Profissional profissionalPersistido = Profissional.create(
                PROFISSIONAL_ID,
                NOME_PROFISSIONAL,
                List.of(Especialidade.create(ESPECIALIDADE_ID, NOME_ESPECIALIDADE)),
                List.of(ServicoOferecido.create(SERVICO_ID, NOME_SERVICO, VALOR_SERVICO.doubleValue())),
                estabelecimento,
                List.of(HorarioDisponivel.create(HORARIO_ID, HORARIO_DISPONIVEL))
        );

        IProfissionalGateway profissionalGateway = mock(IProfissionalGateway.class);
        IEstabelecimentoGateway estabelecimentoGateway = mock(IEstabelecimentoGateway.class);
        AdicionarProfissionalValidationChain validationChain = mock(AdicionarProfissionalValidationChain.class);

        when(estabelecimentoGateway.buscarEstabelecimento(ESTABELECIMENTO_ID)).thenReturn(estabelecimento);
        when(profissionalGateway.criarProfissional(any())).thenReturn(profissionalPersistido);

        AdicionarProfissionalUseCase useCase = AdicionarProfissionalUseCase.create(
                profissionalGateway,
                validationChain,
                estabelecimentoGateway
        );

        OutputAdicionarProfissional output = useCase.execute(input);

        ArgumentCaptor<Profissional> validationCaptor = ArgumentCaptor.forClass(Profissional.class);
        ArgumentCaptor<Profissional> gatewayCaptor = ArgumentCaptor.forClass(Profissional.class);

        verify(validationChain, times(1)).validate(validationCaptor.capture());
        verify(profissionalGateway, times(1)).criarProfissional(gatewayCaptor.capture());

        Profissional profissionalValidado = validationCaptor.getValue();
        Profissional profissionalEnviado = gatewayCaptor.getValue();

        assertNotNull(output);
        assertEquals(PROFISSIONAL_ID, output.id());
        assertEquals(NOME_PROFISSIONAL, output.nome());
        assertEquals(ESTABELECIMENTO_ID, output.idEstabelecimento());
        assertEquals(1, output.especialidades().size());
        assertEquals(ESPECIALIDADE_ID, output.especialidades().getFirst().id());
        assertEquals(NOME_ESPECIALIDADE, output.especialidades().getFirst().nome());
        assertEquals(1, output.servicoOferecidos().size());
        assertEquals(SERVICO_ID, output.servicoOferecidos().getFirst().id());
        assertEquals(NOME_SERVICO, output.servicoOferecidos().getFirst().nome());
        assertEquals(VALOR_SERVICO, output.servicoOferecidos().getFirst().valor());
        assertEquals(1, output.horariosDisponiveis().size());
        assertEquals(HORARIO_ID, output.horariosDisponiveis().getFirst().id());
        assertEquals(HORARIO_DISPONIVEL, output.horariosDisponiveis().getFirst().horario());

        assertEquals(NOME_PROFISSIONAL, profissionalValidado.getNome());
        assertEquals(ESTABELECIMENTO_ID, profissionalValidado.getEstabelecimento().getId());
        assertEquals(1, profissionalValidado.getEspecialidades().size());
        assertEquals(NOME_ESPECIALIDADE, profissionalValidado.getEspecialidades().getFirst().getNome());
        assertEquals(1, profissionalValidado.getServicoOferecidos().size());
        assertEquals(NOME_SERVICO, profissionalValidado.getServicoOferecidos().getFirst().getNome());
        assertEquals(VALOR_SERVICO.doubleValue(), profissionalValidado.getServicoOferecidos().getFirst().getValor());
        assertEquals(1, profissionalValidado.getHorarioDisponivel().size());
        assertEquals(HORARIO_DISPONIVEL, profissionalValidado.getHorarioDisponivel().getFirst().getHorario());

        assertEquals(profissionalValidado.getNome(), profissionalEnviado.getNome());
        assertEquals(profissionalValidado.getEstabelecimento().getId(), profissionalEnviado.getEstabelecimento().getId());
        assertEquals(profissionalValidado.getEspecialidades().getFirst().getNome(), profissionalEnviado.getEspecialidades().getFirst().getNome());
        assertEquals(profissionalValidado.getServicoOferecidos().getFirst().getNome(), profissionalEnviado.getServicoOferecidos().getFirst().getNome());
        assertEquals(profissionalValidado.getHorarioDisponivel().getFirst().getHorario(), profissionalEnviado.getHorarioDisponivel().getFirst().getHorario());
    }

    @DisplayName("Nao deve adicionar profissional quando estabelecimento nao existir")
    @Test
    void naoDeveAdicionarProfissionalQuandoEstabelecimentoNaoExistir() {
        InputAdicionarProfissional input = new InputAdicionarProfissional(
                NOME_PROFISSIONAL,
                List.of(new InputEspecialidade(NOME_ESPECIALIDADE)),
                List.of(new InputServicoOferecido(NOME_SERVICO, VALOR_SERVICO)),
                ESTABELECIMENTO_ID,
                List.of(new InputHorarioDisponivel(HORARIO_DISPONIVEL))
        );

        IProfissionalGateway profissionalGateway = mock(IProfissionalGateway.class);
        IEstabelecimentoGateway estabelecimentoGateway = mock(IEstabelecimentoGateway.class);

        when(estabelecimentoGateway.buscarEstabelecimento(ESTABELECIMENTO_ID)).thenReturn(null);

        AdicionarProfissionalValidationChain validationChain = new AdicionarProfissionalValidationChain(
                List.of(new EstabelecimentoNaoExisteHandler(estabelecimentoGateway))
        );

        AdicionarProfissionalUseCase useCase = AdicionarProfissionalUseCase.create(
                profissionalGateway,
                validationChain,
                estabelecimentoGateway
        );

        EstabelecimentoNaoExisteException thrown = assertThrows(
                EstabelecimentoNaoExisteException.class,
                () -> useCase.execute(input)
        );

        assertEquals("Estabelecimento não encontrado", thrown.getMessage());
        verify(estabelecimentoGateway, times(1)).buscarEstabelecimento(ESTABELECIMENTO_ID);
        verify(profissionalGateway, never()).criarProfissional(any());
    }
}
