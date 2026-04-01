package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto;

import java.util.List;

public record EstabelecimentoDTOOutput(
        String id,
        String nome,
        String horarioAbertura,
        String horarioFechamento,
        EnderecoDTO endereco,
        Float nota,
        List<ProfissionalDTOOutput> profissionais
) {
}
