package com.fiap.pos_tech.agendamento_servicos.infrastructure.gateway;

import com.fiap.pos_tech.agendamento_servicos.application.gateway.IEstabelecimentoGateway;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Endereco;
import com.fiap.pos_tech.agendamento_servicos.domain.model.Estabelecimento;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EstabelecimentoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.entity.EnderecoEntity;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.persistence.repository.EstabelecimentoJPARepository;
import com.fiap.pos_tech.agendamento_servicos.infrastructure.presenters.EstabelecimentoPresenter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.domain.Specification;

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
    public List<Estabelecimento> buscarEstabelecimentos(Estabelecimento estabelecimento) {
        Specification<EstabelecimentoEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (estabelecimento == null) {
                return criteriaBuilder.conjunction();
            }

            if (estabelecimento.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), estabelecimento.getId()));
            }

            if (temTexto(estabelecimento.getNome())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + estabelecimento.getNome().toLowerCase() + "%"
                ));
            }

            if (estabelecimento.getHorarioAbertura() != null) {
                predicates.add(criteriaBuilder.equal(root.get("horarioAbertura"), estabelecimento.getHorarioAbertura()));
            }

            if (estabelecimento.getHorarioFechamento() != null) {
                predicates.add(criteriaBuilder.equal(root.get("horarioFechamento"), estabelecimento.getHorarioFechamento()));
            }

            if (estabelecimento.getEndereco() != null) {
                adicionarFiltrosEndereco(estabelecimento.getEndereco(), root.join("endereco"), criteriaBuilder, predicates);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return estabelecimentoJPARepository.findAll(specification)
                .stream()
                .map(EstabelecimentoPresenter::toDomain)
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
            jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder,
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

    private static void adicionarLikeSePreenchido(
            List<Predicate> predicates,
            jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder,
            Join<EstabelecimentoEntity, EnderecoEntity> enderecoJoin,
            String campo,
            String valor
    ) {
        if (!temTexto(valor)) {
            return;
        }

        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(enderecoJoin.get(campo)),
                "%" + valor.toLowerCase() + "%"
        ));
    }

    private static boolean temTexto(String valor) {
        return valor != null && !valor.isBlank();
    }
}
