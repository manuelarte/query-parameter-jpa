package io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public interface OperatorPredicate<V> {

  Predicate getPredicateByKeyAndValue(final Path path, final V value,
      final CriteriaBuilder criteriaBuilder);

}
