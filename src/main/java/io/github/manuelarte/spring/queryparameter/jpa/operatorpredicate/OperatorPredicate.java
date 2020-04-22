package io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate;

import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public interface OperatorPredicate<V> extends OperatorQuery<V, Predicate> {

  Predicate getPredicateByKeyAndValue(final Path path, final V value,
      final CriteriaBuilder criteriaBuilder);

}
