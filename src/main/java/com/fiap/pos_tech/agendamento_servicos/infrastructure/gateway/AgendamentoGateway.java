package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AgendamentoGateway implements IAgendamentoGateway {

    @Override
    public Agendamento criarAgendamento(Agendamento agendamento) {
        return null;
    }

    @Override
    public void enviarNotificacaoAgendamento(Agendamento agendamento) {

    }

    @Override
    public Agendamento atualizarAgendamento(Agendamento agendamento) {
        return null;
    }

    @Override
    public Agendamento cancelarAgendamento(UUID agendamentoId) {
        return null;
    }

    @Override
    public Agendamento registrarAusenciaAgendamento(UUID agendamentoId) {
        return null;
    }

    @Override
    public Agendamento buscarAgendamento(Estabelecimento estabelecimento, LocalDateTime horario, Profissional profissional) {
        return null;
    }

    @Override
    public Agendamento buscarAgendamento(UUID agendamentoId) {
        return null;
    }
}
