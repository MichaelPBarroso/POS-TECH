package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="especialidade")
public class EspecialidadeEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalEntity profissional;
}
