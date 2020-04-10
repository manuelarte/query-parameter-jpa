package io.github.manuelarte.spring.queryparameter.jpa.model;

import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.util.TriPredicate;

public interface OperatorPredicateProvider {

  void addOperatorPredicateSelector(TriPredicate<Class<?>, String, Operator<Object>> predicate,
      OperatorPredicate<Object> operatorPredicate);

  OperatorPredicate<Object> getOperatorPredicate(Class<?> entity, String criterionKey,
      Operator<Object> operator);

}
