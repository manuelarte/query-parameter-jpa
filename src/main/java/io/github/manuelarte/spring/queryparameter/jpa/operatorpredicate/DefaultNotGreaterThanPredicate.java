package io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class DefaultNotGreaterThanPredicate<V> implements OperatorPredicate<V> {

  @Override
  public Predicate getPredicateByKeyAndValue(final Path path, final V value,
      final CriteriaBuilder criteriaBuilder) {
    return criteriaBuilder.greaterThan(path, (Comparable<Object>)value).not();
  }

}
