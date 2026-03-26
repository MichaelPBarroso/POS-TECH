package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="profissional")
public class ProfissionalEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private String nome;

    @OneToMany
    private List<EspecialidadeEntity> especialidades;

    @OneToMany
    private List<ServicoOferecidoEntity> servicoOferecidos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private EstabelecimentoEntity estabelecimento;
}
