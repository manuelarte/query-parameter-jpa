package org.manuel.spring.queryparamter.jpa.operatorpredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class DefaultEqualsPredicate<V> implements OperatorPredicate<V> {

  public Predicate getPredicateByKeyAndValue(final Path path, final V value,
      final CriteriaBuilder criteriaBuilder) {
    return criteriaBuilder.equal(path, value);
  }

}
