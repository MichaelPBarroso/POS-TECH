package com.fiap.pos_tech.agendamento_servicos.infrastructure.schedule;

import com.fiap.pos_tech.agendamento_servicos.application.usecase.lembreteAgendamento.LembreteAgendamentoUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LembreteAgendamentoSchedule {

    private final LembreteAgendamentoUseCase lembreteAgendamentoUseCase;

    public LembreteAgendamentoSchedule(LembreteAgendamentoUseCase lembreteAgendamentoUseCase) {
        this.lembreteAgendamentoUseCase = lembreteAgendamentoUseCase;
    }

    @Scheduled(
            cron = "${scheduler.lembrete-agendamento.cron:0 0 8 * * *}",
            zone = "${scheduler.lembrete-agendamento.zone:America/Sao_Paulo}"
    )
    public void executarLembreteAgendamentoDiario() {
        lembreteAgendamentoUseCase.execute();
    }
}
