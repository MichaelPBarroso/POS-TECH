package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.domain.model.FiltroAvancado;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EstabelecimentoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EnderecoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.HorarioDisponivelEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ProfissionalEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.ServicoOferecidoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.EstabelecimentoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.EstabelecimentoPresenter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class EstabelecimentoGateway implements IEstabelecimentoGateway {

    private final EstabelecimentoJPARepository estabelecimentoJPARepository;

    @Override
    public Estabelecimento criarEstabelecimento(Estabelecimento estabelecimento) {
        EstabelecimentoEntity estabelecimentoEntity = estabelecimentoJPARepository.save(EstabelecimentoPresenter.toEntity(estabelecimento));

        return EstabelecimentoPresenter.toDomain(estabelecimentoEntity);
    }

    @Override
    public List<Estabelecimento> buscarEstabelecimentos(FiltroAvancado filtroAvancado) {
        Specification<EstabelecimentoEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtroAvancado == null) {
                return criteriaBuilder.conjunction();
            }

            query.distinct(true);

            if (filtroAvancado.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtroAvancado.getId()));
            }

            if (temTexto(filtroAvancado.getNome())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + filtroAvancado.getNome().toLowerCase() + "%"
                ));
            }

            if (filtroAvancado.getHorarioAbertura() != null) {
                predicates.add(criteriaBuilder.equal(root.get("horarioAbertura"), filtroAvancado.getHorarioAbertura()));
            }

            if (filtroAvancado.getHorarioFechamento() != null) {
                predicates.add(criteriaBuilder.equal(root.get("horarioFechamento"), filtroAvancado.getHorarioFechamento()));
            }

            if (filtroAvancado.getNotaMaiorQue() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("mediaNotas"),
                        BigDecimal.valueOf(filtroAvancado.getNotaMaiorQue())
                ));
            }

            if (filtroAvancado.getNotaMenorQue() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("mediaNotas"),
                        BigDecimal.valueOf(filtroAvancado.getNotaMenorQue())
                ));
            }

            if (filtroAvancado.getEndereco() != null) {
                adicionarFiltrosEndereco(filtroAvancado.getEndereco(), root.join("endereco", JoinType.INNER), criteriaBuilder, predicates);
            }

            boolean precisaJoinProfissional = filtroAvancado.getProfissional() != null
                    || filtroAvancado.getServicoOferecido() != null
                    || filtroAvancado.getHorarioDisponivel() != null;

            if (precisaJoinProfissional) {
                Join<EstabelecimentoEntity, ProfissionalEntity> profissionalJoin = root.join("profissionais", JoinType.INNER);
                adicionarFiltrosProfissional(filtroAvancado, profissionalJoin, criteriaBuilder, predicates);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return estabelecimentoJPARepository.findAll(specification)
                .stream()
                .map(EstabelecimentoPresenter::toDomainComProfissionais)
                .toList();
    }

    @Override
    public Estabelecimento buscarEstabelecimento(UUID idEstabelecimento) {
        Optional<EstabelecimentoEntity> estabelecimentoEntity = estabelecimentoJPARepository.findById(idEstabelecimento);

        return estabelecimentoEntity.map(EstabelecimentoPresenter::toDomain).orElse(null);
    }

    private static void adicionarFiltrosEndereco(
            Endereco endereco,
            Join<EstabelecimentoEntity, EnderecoEntity> enderecoJoin,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates
    ) {
        if (endereco == null) {
            return;
        }

        if (endereco.getId() != null) {
            predicates.add(criteriaBuilder.equal(enderecoJoin.get("id"), endereco.getId()));
        }

        adicionarLikeSePreenchido(predicates, criteriaBuilder, enderecoJoin, "logradouro", endereco.getLogradouro());
        adicionarLikeSePreenchido(predicates, criteriaBuilder, enderecoJoin, "numero", endereco.getNumero());
        adicionarLikeSePreenchido(predicates, criteriaBuilder, enderecoJoin, "complemento", endereco.getComplemento());
        adicionarLikeSePreenchido(predicates, criteriaBuilder, enderecoJoin, "bairro", endereco.getBairro());
        adicionarLikeSePreenchido(predicates, criteriaBuilder, enderecoJoin, "cidade", endereco.getCidade());
        adicionarLikeSePreenchido(predicates, criteriaBuilder, enderecoJoin, "estado", endereco.getEstado());
        adicionarLikeSePreenchido(predicates, criteriaBuilder, enderecoJoin, "cep", endereco.getCep());
    }

    private static void adicionarFiltrosProfissional(
            FiltroAvancado filtroAvancado,
            Join<EstabelecimentoEntity, ProfissionalEntity> profissionalJoin,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates
    ) {
        if (filtroAvancado.getProfissional() != null) {
            if (filtroAvancado.getProfissional().getId() != null) {
                predicates.add(criteriaBuilder.equal(profissionalJoin.get("id"), filtroAvancado.getProfissional().getId()));
            }

            adicionarLikeSePreenchido(predicates, criteriaBuilder, profissionalJoin, "nome", filtroAvancado.getProfissional().getNome());
        }

        if (filtroAvancado.getServicoOferecido() != null) {
            Join<ProfissionalEntity, ServicoOferecidoEntity> servicoJoin = profissionalJoin.join("servicoOferecidos", JoinType.INNER);

            if (filtroAvancado.getServicoOferecido().getId() != null) {
                predicates.add(criteriaBuilder.equal(servicoJoin.get("id"), filtroAvancado.getServicoOferecido().getId()));
            }

            adicionarLikeSePreenchido(predicates, criteriaBuilder, servicoJoin, "nome", filtroAvancado.getServicoOferecido().getNome());

            if (filtroAvancado.getValorMaiorQue() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(servicoJoin.get("valor"), filtroAvancado.getValorMaiorQue().doubleValue()));
            }

            if (filtroAvancado.getValorMenorQue() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(servicoJoin.get("valor"), filtroAvancado.getValorMenorQue().doubleValue()));
            }
        }

        if (filtroAvancado.getHorarioDisponivel() != null) {
            Join<ProfissionalEntity, HorarioDisponivelEntity> horarioJoin = profissionalJoin.join("horariosDisponiveis", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(horarioJoin.get("horario"), filtroAvancado.getHorarioDisponivel()));
        }
    }

    private static <T> void adicionarLikeSePreenchido(
            List<Predicate> predicates,
            CriteriaBuilder criteriaBuilder,
            Join<?, T> join,
            String campo,
            String valor
    ) {
        if (!temTexto(valor)) {
            return;
        }

        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(join.get(campo).as(String.class)),
                "%" + valor.toLowerCase() + "%"
        ));
    }

    private static boolean temTexto(String valor) {
        return valor != null && !valor.isBlank();
    }
}
