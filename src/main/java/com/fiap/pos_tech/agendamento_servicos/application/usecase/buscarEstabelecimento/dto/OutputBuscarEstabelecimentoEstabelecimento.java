package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record OutputBuscarEstabelecimentoEstabelecimento(
        UUID id,
        String nome,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        OutputBuscarEstabelecimentoEndereco endereco,
        BigDecimal nota,
        List<OutputBuscarEstabelecimentoProfissional> profissionais,
        List<OutputBuscarEstabelecimentoFotos> fotoEstabelecimento
) {
}
