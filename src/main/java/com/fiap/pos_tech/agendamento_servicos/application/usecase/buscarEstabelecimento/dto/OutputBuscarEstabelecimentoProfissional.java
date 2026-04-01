package com.fiap.pos_tech.agendamento_servicos.application.usecase.buscarEstabelecimento.dto;

import java.util.List;
import java.util.UUID;

public record OutputBuscarEstabelecimentoProfissional(
        UUID id,
        String nome,
        List<OutputBuscarEstabelecimentoHorarioDisponivel> horariosDisponiveis,
        List<OutputBuscarEstabelecimentoServicoOferecido> servicosOferecidos
) {
}
