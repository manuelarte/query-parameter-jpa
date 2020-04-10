package org.manuel.spring.queryparamter.jpa.model;

import org.manuel.spring.queryparameter.operators.Operator;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.OperatorPredicate;
import org.manuel.spring.queryparamter.jpa.util.TriPredicate;

public interface OperatorsPredicateProvider {

  void addOperatorPredicateSelector(TriPredicate<Class<?>, String, Operator<Object>> predicate,
      OperatorPredicate<Object> operatorPredicate);

  OperatorPredicate<Object> getOperatorPredicate(Class<?> entity, String criterionKey,
      Operator<Object> operator);

}
