package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto;

import java.util.List;
import java.util.UUID;

public record OutputAdicionarProfissional(UUID id, String nome, List<OutputEspecialidade> especialidades, List<OutputServicoOferecido> servicoOferecidos, UUID idEstabelecimento, List<OutputHorarioDisponivel> horariosDisponiveis) {
}
