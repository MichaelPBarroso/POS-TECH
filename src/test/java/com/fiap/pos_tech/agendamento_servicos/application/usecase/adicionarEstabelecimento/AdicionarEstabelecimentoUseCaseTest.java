package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.InputEndereco;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.InputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.dto.OutputEstabelecimento;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarEstabelecimento.validation.AdicionarEstabelecimentoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalTime;
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
import static org.mockito.Mockito.doThrow;

class AdicionarEstabelecimentoUseCaseTest {

    private static final UUID ESTABELECIMENTO_ID = UUID.randomUUID();
    private static final UUID ENDERECO_ID = UUID.randomUUID();
    private static final String LOGRADOURO = "Rua Brigadeiro";
    private static final String NUMERO = "2334";
    private static final String COMPLEMENTO = null;
    private static final String BAIRRO = "Taboao da Serra";
    private static final String CIDADE = "Sao Paulo";
    private static final String ESTADO = "Sao Paulo";
    private static final String CEP = "00000-323";
    private static final String NOME_ESTABELECIMENTO = "Estabelecimento 1";
    private static final LocalTime HORARIO_ABERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORARIO_FECHAMENTO = LocalTime.of(21, 0);

    @DisplayName("Adicionar estabelecimento com sucesso")
    @Test
    void adicionarEstabelecimento() {
        InputEndereco inputEndereco = new InputEndereco(
                LOGRADOURO,
                NUMERO,
                COMPLEMENTO,
                BAIRRO,
                CIDADE,
                ESTADO,
                CEP
        );
        InputEstabelecimento inputEstabelecimento = new InputEstabelecimento(
                NOME_ESTABELECIMENTO,
                HORARIO_ABERTURA,
                HORARIO_FECHAMENTO,
                inputEndereco
        );

        Endereco endereco = Endereco.create(
                ENDERECO_ID,
                LOGRADOURO,
                NUMERO,
                COMPLEMENTO,
                BAIRRO,
                CIDADE,
                ESTADO,
                CEP
        );
        Estabelecimento estabelecimento = Estabelecimento.create(
                ESTABELECIMENTO_ID,
                NOME_ESTABELECIMENTO,
                HORARIO_ABERTURA,
                HORARIO_FECHAMENTO,
                endereco,
                (java.math.BigDecimal) null
        );

        IEstabelecimentoGateway estabelecimentoGateway = mock(IEstabelecimentoGateway.class);
        AdicionarEstabelecimentoValidationChain validationChain = mock(AdicionarEstabelecimentoValidationChain.class);

        when(estabelecimentoGateway.criarEstabelecimento(any())).thenReturn(estabelecimento);

        AdicionarEstabelecimentoUseCase useCase =
                AdicionarEstabelecimentoUseCase.create(estabelecimentoGateway, validationChain);

        OutputEstabelecimento output = useCase.execute(inputEstabelecimento);

        ArgumentCaptor<Estabelecimento> validationCaptor = ArgumentCaptor.forClass(Estabelecimento.class);
        ArgumentCaptor<Estabelecimento> gatewayCaptor = ArgumentCaptor.forClass(Estabelecimento.class);

        verify(validationChain, times(1)).validate(validationCaptor.capture());
        verify(estabelecimentoGateway, times(1)).criarEstabelecimento(gatewayCaptor.capture());

        Estabelecimento estabelecimentoValidado = validationCaptor.getValue();
        Estabelecimento estabelecimentoEnviado = gatewayCaptor.getValue();

        assertNotNull(output);
        assertEquals(ESTABELECIMENTO_ID, output.id());
        assertEquals(NOME_ESTABELECIMENTO, output.nome());
        assertEquals(HORARIO_ABERTURA, output.horarioAbertura());
        assertEquals(HORARIO_FECHAMENTO, output.horarioFechamento());
        assertEquals(ENDERECO_ID, output.endereco().id());
        assertEquals(LOGRADOURO, output.endereco().logradouro());
        assertEquals(NUMERO, output.endereco().numero());
        assertEquals(COMPLEMENTO, output.endereco().complemento());
        assertEquals(BAIRRO, output.endereco().bairro());
        assertEquals(CIDADE, output.endereco().cidade());
        assertEquals(ESTADO, output.endereco().estado());
        assertEquals(CEP, output.endereco().cep());

        assertEquals(NOME_ESTABELECIMENTO, estabelecimentoEnviado.getNome());
        assertEquals(HORARIO_ABERTURA, estabelecimentoEnviado.getHorarioAbertura());
        assertEquals(HORARIO_FECHAMENTO, estabelecimentoEnviado.getHorarioFechamento());
        assertNotNull(estabelecimentoEnviado.getEndereco());
        assertEquals(LOGRADOURO, estabelecimentoEnviado.getEndereco().getLogradouro());
        assertEquals(NUMERO, estabelecimentoEnviado.getEndereco().getNumero());
        assertEquals(COMPLEMENTO, estabelecimentoEnviado.getEndereco().getComplemento());
        assertEquals(BAIRRO, estabelecimentoEnviado.getEndereco().getBairro());
        assertEquals(CIDADE, estabelecimentoEnviado.getEndereco().getCidade());
        assertEquals(ESTADO, estabelecimentoEnviado.getEndereco().getEstado());
        assertEquals(CEP, estabelecimentoEnviado.getEndereco().getCep());

        assertEquals(estabelecimentoValidado.getNome(), estabelecimentoEnviado.getNome());
        assertEquals(estabelecimentoValidado.getHorarioAbertura(), estabelecimentoEnviado.getHorarioAbertura());
        assertEquals(estabelecimentoValidado.getHorarioFechamento(), estabelecimentoEnviado.getHorarioFechamento());
        assertEquals(estabelecimentoValidado.getEndereco().getLogradouro(), estabelecimentoEnviado.getEndereco().getLogradouro());
    }

    @DisplayName("Nao deve adicionar estabelecimento quando a validacao falhar")
    @Test
    void naoDeveAdicionarEstabelecimentoQuandoValidacaoFalhar() {
        InputEndereco inputEndereco = new InputEndereco(
                LOGRADOURO,
                NUMERO,
                COMPLEMENTO,
                BAIRRO,
                CIDADE,
                ESTADO,
                CEP
        );
        InputEstabelecimento inputEstabelecimento = new InputEstabelecimento(
                NOME_ESTABELECIMENTO,
                HORARIO_ABERTURA,
                HORARIO_FECHAMENTO,
                inputEndereco
        );

        IEstabelecimentoGateway estabelecimentoGateway = mock(IEstabelecimentoGateway.class);
        AdicionarEstabelecimentoValidationChain validationChain = mock(AdicionarEstabelecimentoValidationChain.class);

        RuntimeException exception = new RuntimeException("Dados invalidos para estabelecimento");
        doThrow(exception).when(validationChain).validate(any());

        AdicionarEstabelecimentoUseCase useCase =
                AdicionarEstabelecimentoUseCase.create(estabelecimentoGateway, validationChain);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> useCase.execute(inputEstabelecimento));

        assertEquals("Dados invalidos para estabelecimento", thrown.getMessage());
        verify(validationChain, times(1)).validate(any());
        verify(estabelecimentoGateway, never()).criarEstabelecimento(any());
    }
}
