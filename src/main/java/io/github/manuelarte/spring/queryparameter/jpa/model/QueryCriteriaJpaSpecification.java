package io.github.manuelarte.spring.queryparameter.jpa.model;

import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerProvider;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQueryProvider;
import io.github.manuelarte.spring.queryparameter.query.BooleanOperator;
import io.github.manuelarte.spring.queryparameter.query.OtherCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.query.QueryCriterion;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class QueryCriteriaJpaSpecification<T> implements Specification<T> {

  private final Class<T> entity;
  private final QueryCriteria queryCriteria;
  private final TypeTransformerProvider typeTransformerProvider;
  private final OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorPredicateProvider;

  public QueryCriteriaJpaSpecification(
      final Class<T> entity,
      final QueryCriteria queryCriteria,
      final TypeTransformerProvider typeTransformerProvider,
      final OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorPredicateProvider) {
    this.entity = entity;
    this.queryCriteria = queryCriteria;
    this.typeTransformerProvider = typeTransformerProvider;
    this.operatorPredicateProvider = operatorPredicateProvider;
  }

  @Override
  public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
      final CriteriaBuilder criteriaBuilder) {
    final QueryCriterion<Object> queryCriterion = queryCriteria.getCriterion();
    Predicate predicate = addPredicate(root, queryCriterion, criteriaBuilder);
    Optional<OtherCriteria> otherCriteria = queryCriteria.getOther();
    while (otherCriteria.isPresent()) {
      OtherCriteria other = otherCriteria.get();
      final QueryCriteria newCriteria = other.getCriteria();
      if (other.getOperator() == BooleanOperator.AND) {
        predicate = criteriaBuilder.and(predicate, addPredicate(root, newCriteria.getCriterion(),
            criteriaBuilder));
      } else if (other.getOperator() == BooleanOperator.OR) {
        predicate = criteriaBuilder.or(predicate, addPredicate(root, newCriteria.getCriterion(),
            criteriaBuilder));
      }
      otherCriteria = newCriteria.getOther();
    }
    return predicate;
  }

  private Predicate addPredicate(final Root<T> root, final QueryCriterion<?> queryCriterion,
      final CriteriaBuilder criteriaBuilder) {
    // check if it's a list
    final Object castedValue;
    if (queryCriterion.getValue() instanceof List) {
      castedValue = ((List) queryCriterion.getValue()).stream()
          .map(it -> typeTransformerProvider.getTransformer(entity,
              queryCriterion.getKey()).transformValue(entity, queryCriterion.getKey(),
              it)).collect(Collectors.toList());
    } else {
      castedValue = typeTransformerProvider.getTransformer(entity,
          queryCriterion.getKey()).transformValue(entity, queryCriterion.getKey(),
          queryCriterion.getValue());
    }
    final OperatorPredicate<Object> operatorPredicate = operatorPredicateProvider
        .getOperatorQuery(entity, queryCriterion.getKey(),
            (Operator<Object>) queryCriterion.getOperator());
    From join = root;
    final String[] attributes = queryCriterion.getKey().split("\\.", -1);
    for (int i = 0, attributesLength = attributes.length - 1; i < attributesLength; i++) {
      join = join.join(attributes[i], JoinType.LEFT);
    }
    final Path path = join.get(attributes[attributes.length - 1]);
    return operatorPredicate.getPredicateByKeyAndValue(path, castedValue, criteriaBuilder);
  }
}
