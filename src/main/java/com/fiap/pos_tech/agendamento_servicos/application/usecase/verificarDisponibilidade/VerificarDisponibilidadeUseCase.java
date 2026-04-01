package com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IAgendamentoGateway;
import com.fiap.pos_tech.agendamento_servicos.application.gateway.IProfissionalGateway;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.InputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.application.usecase.verificarDisponibilidade.dto.OutputVerificarDisponibilidade;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Agendamento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.HorarioDisponivel;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Profissional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class VerificarDisponibilidadeUseCase {

    private IAgendamentoGateway agendamentoGateway;
    private IProfissionalGateway profissionalGateway;

    private VerificarDisponibilidadeUseCase(IAgendamentoGateway agendamentoGateway, IProfissionalGateway profissionalGateway){
        this.agendamentoGateway = agendamentoGateway;
        this.profissionalGateway = profissionalGateway;
    }

    public static VerificarDisponibilidadeUseCase create(IAgendamentoGateway agendamentoGateway, IProfissionalGateway profissionalGateway){
        return new VerificarDisponibilidadeUseCase(agendamentoGateway, profissionalGateway);
    }

    public OutputVerificarDisponibilidade execute(InputVerificarDisponibilidade input){

        UUID profissionalId = input.profissionalId();

        Profissional profissional = profissionalGateway.buscarProfissionalECaracteriscas(profissionalId);

        List<HorarioDisponivel> horariosDisponiveis = profissional.getHorarioDisponivel();

        List<Agendamento> agendamentos = agendamentoGateway.buscarAgendamentos(profissional.getEstabelecimento(), input.data(), profissional);

        if (agendamentos == null || agendamentos.isEmpty()) {
            return new OutputVerificarDisponibilidade(horariosDisponiveis);
        }

        Set<java.time.LocalTime> horariosAgendados = agendamentos.stream()
                .map(Agendamento::getHorario)
                .collect(Collectors.toSet());

        List<HorarioDisponivel> horariosFiltrados = horariosDisponiveis.stream()
                .filter(horarioDisponivel -> !horariosAgendados.contains(horarioDisponivel.getHorario()))
                .toList();

        return new OutputVerificarDisponibilidade(horariosFiltrados);
    }

}
