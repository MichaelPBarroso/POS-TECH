package com.fiap.pos_tech.agendamento_servicos.application.gateway;

import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IAgendamentoGateway {

    Agendamento criarAgendamento(Agendamento agendamento);

    void enviarNotificacaoAgendamento(Agendamento agendamento);

    Agendamento atualizarAgendamento(Agendamento agendamento);

    Agendamento cancelarAgendamento(UUID agendamentoId);

    Agendamento registrarAusenciaAgendamento(UUID agendamentoId);

    Agendamento buscarAgendamento(Estabelecimento estabelecimento, LocalDateTime horario, Profissional profissional);

    Agendamento buscarAgendamento(UUID agendamentoId);
}
