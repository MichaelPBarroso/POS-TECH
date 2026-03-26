package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity;

import com.fiap.pos_tech.agendamento_servicos.domain.model.StatusAgendamentoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="agendamento")
public class AgendamentoEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private LocalDateTime horario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servico_id", nullable = false)
    private ServicoOferecidoEntity servicoOferecido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private EstabelecimentoEntity estabelecimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalEntity profissional;

    @Enumerated(EnumType.STRING)
    private StatusAgendamentoEnum statusAgendamentoEnum;
}
