package com.fiap.pos_tech.agendamento_servicos.application.usecase.adicionarProfissional.dto;

import java.util.List;
import java.util.UUID;

public record InputAdicionarProfissional(String nome, List<InputEspecialidade> especialidades, List<InputServicoOferecido> servicoOferecidos, UUID idEstabelecimento, List<InputHorarioDisponivel> horariosDisponiveis) {

}
