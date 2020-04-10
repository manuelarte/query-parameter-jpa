package io.github.manuelarte.spring.queryparameter.jpa.model;

import io.github.manuelarte.spring.queryparameter.jpa.util.TriPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.operators.Operator;

public interface OperatorsPredicateProvider {

  void addOperatorPredicateSelector(TriPredicate<Class<?>, String, Operator<Object>> predicate,
      OperatorPredicate<Object> operatorPredicate);

  OperatorPredicate<Object> getOperatorPredicate(Class<?> entity, String criterionKey,
      Operator<Object> operator);

}
