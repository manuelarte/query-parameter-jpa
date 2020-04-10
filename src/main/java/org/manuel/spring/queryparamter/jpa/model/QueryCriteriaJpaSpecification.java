package org.manuel.spring.queryparamter.jpa.model;

import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.manuel.spring.queryparameter.operators.Operator;
import org.manuel.spring.queryparameter.query.BooleanOperator;
import org.manuel.spring.queryparameter.query.OtherCriteria;
import org.manuel.spring.queryparameter.query.QueryCriteria;
import org.manuel.spring.queryparameter.query.QueryCriterion;
import org.manuel.spring.queryparameter.transformers.TypeTransformerProvider;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.OperatorPredicate;
import org.springframework.data.jpa.domain.Specification;

public class QueryCriteriaJpaSpecification<T> implements Specification<T> {

  private final Class<T> entity;
  private final QueryCriteria queryCriteria;
  private final TypeTransformerProvider typeTransformerProvider;
  private final OperatorsPredicateProvider operatorsPredicateProvider;

  public QueryCriteriaJpaSpecification(
      final Class<T> entity,
      final QueryCriteria queryCriteria,
      final TypeTransformerProvider typeTransformerProvider,
      final OperatorsPredicateProvider operatorsPredicateProvider) {
    this.entity = entity;
    this.queryCriteria = queryCriteria;
    this.typeTransformerProvider = typeTransformerProvider;
    this.operatorsPredicateProvider = operatorsPredicateProvider;
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
    final Object castedValue = typeTransformerProvider.getTransformer(entity,
        queryCriterion.getKey()).transformValue(entity, queryCriterion.getKey(),
        queryCriterion.getValue());
    final OperatorPredicate<Object> operatorPredicate = operatorsPredicateProvider
        .getOperatorPredicate(entity, queryCriterion.getKey(),
            (Operator<Object>) queryCriterion.getOperator());
    From join = root;
    final String[] attributes = queryCriterion.getKey().split("\\.");
    for (int i = 0, attributesLength = attributes.length - 1; i < attributesLength; i++) {
      join = join.join(attributes[i], JoinType.LEFT);
    }
    final Path path = join.get(attributes[attributes.length - 1]);
    return operatorPredicate.getPredicateByKeyAndValue(path, castedValue, criteriaBuilder);
  }
}
