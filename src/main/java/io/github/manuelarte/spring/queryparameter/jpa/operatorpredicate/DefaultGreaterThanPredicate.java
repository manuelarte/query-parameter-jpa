package io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class DefaultGreaterThanPredicate<V> implements OperatorPredicate<V> {

  public Predicate getPredicateByKeyAndValue(final Path path, final V value,
      final CriteriaBuilder criteriaBuilder) {
    return criteriaBuilder.greaterThan(path, (Comparable) value);
  }

}
