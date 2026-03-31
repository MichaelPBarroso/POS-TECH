package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalTime;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="horario_disponivel")
public class HorarioDisponivelEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private LocalTime horario;

    @ManyToOne

    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalEntity profissional;

}
