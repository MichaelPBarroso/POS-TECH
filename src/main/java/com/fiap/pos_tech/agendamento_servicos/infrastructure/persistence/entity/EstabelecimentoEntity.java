package com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="estabelecimento")
public class EstabelecimentoEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private String nome;

    private LocalTime horarioAbertura;

    private LocalTime horarioFechamento;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", nullable = false)
    private EnderecoEntity endereco;

    @OneToMany(mappedBy = "estabelecimento")
    private List<ProfissionalEntity> profissionais;

    private BigDecimal mediaNotas;

    @OneToMany(mappedBy = "estabelecimento")
    private List<FotoEstabelecimentoEntity> fotos;

}
