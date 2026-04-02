package com.fiap.pos_tech.agendamento_servicos.infrastructure.api.dto;

import java.util.List;

public record ProfissionalDTOOutput(
        String id,
        String nome,
        String email,
        List<HorarioDisponivelDTOOutput> horariosDisponiveis,
        List<ServicoDTOOutput> servicosOferecidos
) {
}
