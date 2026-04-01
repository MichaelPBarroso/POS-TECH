package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto;

import com.fiap.pos_tech.agendamento_servicos.application.exceptions.EstabelecimentoNaoExisteException;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IFotoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.dto.InputAdicionarFotos;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.dto.OutputAdicionarFoto;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation.AdicionarFotoValidationChain;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarFoto.validation.EstabelecimentoNaoExisteHandler;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.FotoEstabelecimento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdicionarFotoUseCaseTest {

    @Test
    @DisplayName("Deve adicionar foto com sucesso")
    void deveAdicionarFotoComSucesso() {
        UUID estabelecimentoId = UUID.randomUUID();
        UUID fotoId = UUID.randomUUID();
        Estabelecimento estabelecimento = criarEstabelecimento(estabelecimentoId);
        MultipartFile foto = new MockMultipartFile("foto", "fachada.jpg", "image/jpeg", "conteudo".getBytes(StandardCharsets.UTF_8));
        FotoEstabelecimento fotoPersistida = FotoEstabelecimento.create(fotoId, estabelecimento, "fachada.jpg");

        IFotoGateway fotoGateway = mock(IFotoGateway.class);
        IEstabelecimentoGateway estabelecimentoGateway = mock(IEstabelecimentoGateway.class);
        AdicionarFotoValidationChain validationChain = mock(AdicionarFotoValidationChain.class);

        when(estabelecimentoGateway.buscarEstabelecimento(estabelecimentoId)).thenReturn(estabelecimento);
        when(fotoGateway.adicionar(any(FotoEstabelecimento.class), any(MultipartFile.class))).thenReturn(fotoPersistida);

        AdicionarFotoUseCase useCase = AdicionarFotoUseCase.create(fotoGateway, estabelecimentoGateway, validationChain);

        OutputAdicionarFoto output = useCase.execute(new InputAdicionarFotos(estabelecimentoId, foto));

        assertNotNull(output);
        assertEquals(fotoId, output.id());
        assertEquals("fachada.jpg", output.urlFoto());
        assertEquals(estabelecimentoId, output.idEstabelecimento());

        verify(estabelecimentoGateway).buscarEstabelecimento(estabelecimentoId);
        verify(validationChain).validate(any(FotoEstabelecimento.class));
        verify(fotoGateway).adicionar(any(FotoEstabelecimento.class), any(MultipartFile.class));
    }

    @Test
    @DisplayName("Nao deve adicionar foto quando estabelecimento nao existir")
    void naoDeveAdicionarFotoQuandoEstabelecimentoNaoExistir() {
        UUID estabelecimentoId = UUID.randomUUID();
        MultipartFile foto = new MockMultipartFile("foto", "fachada.jpg", "image/jpeg", "conteudo".getBytes(StandardCharsets.UTF_8));

        IFotoGateway fotoGateway = mock(IFotoGateway.class);
        IEstabelecimentoGateway estabelecimentoGateway = mock(IEstabelecimentoGateway.class);
        when(estabelecimentoGateway.buscarEstabelecimento(estabelecimentoId)).thenReturn(null);

        AdicionarFotoValidationChain validationChain = new AdicionarFotoValidationChain(
                List.of(new EstabelecimentoNaoExisteHandler(estabelecimentoGateway))
        );

        AdicionarFotoUseCase useCase = AdicionarFotoUseCase.create(fotoGateway, estabelecimentoGateway, validationChain);

        assertThrows(EstabelecimentoNaoExisteException.class, () -> useCase.execute(new InputAdicionarFotos(estabelecimentoId, foto)));

        verify(estabelecimentoGateway).buscarEstabelecimento(estabelecimentoId);
        verify(fotoGateway, never()).adicionar(any(), any());
    }

    private Estabelecimento criarEstabelecimento(UUID estabelecimentoId) {
        Endereco endereco = Endereco.create(UUID.randomUUID(), "Rua C", "30", null, "Centro", "Sao Paulo", "SP", "03000-000");
        return Estabelecimento.create(estabelecimentoId, "Studio Foto", LocalTime.of(8, 0), LocalTime.of(18, 0), endereco, null);
    }
}
