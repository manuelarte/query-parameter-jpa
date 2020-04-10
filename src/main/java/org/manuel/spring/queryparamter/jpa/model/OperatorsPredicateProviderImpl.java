package org.manuel.spring.queryparamter.jpa.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.manuel.spring.queryparameter.operators.Operator;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.OperatorPredicate;
import org.manuel.spring.queryparamter.jpa.util.TriPredicate;

public class OperatorsPredicateProviderImpl implements OperatorsPredicateProvider {

  private final List<OperatorPredicateEntry> operatorPredicateEntries;

  public OperatorsPredicateProviderImpl() {
    this.operatorPredicateEntries = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void addOperatorPredicateSelector(
      final TriPredicate<Class<?>, String, Operator<Object>> predicate,
      final OperatorPredicate<Object> operatorPredicate) {
    this.operatorPredicateEntries.add(new OperatorPredicateEntry(predicate, operatorPredicate));
  }

  @Override
  public OperatorPredicate<Object> getOperatorPredicate(final Class<?> entity,
      final String criterionKey, final Operator<Object> operator) {
    final OperatorPredicateEntry operatorPredicateEntry = operatorPredicateEntries.stream()
        .filter(o -> o.predicate.test(entity, criterionKey, operator)).findFirst()
        .orElseThrow(() -> new RuntimeException("query param operator provider not found"));
    return operatorPredicateEntry.operatorPredicate;
  }

  private static final class OperatorPredicateEntry {

    private final TriPredicate<Class<?>, String, Operator<Object>> predicate;
    private final OperatorPredicate<Object> operatorPredicate;

    public OperatorPredicateEntry(final TriPredicate<Class<?>, String, Operator<Object>> predicate,
        OperatorPredicate<Object> operatorPredicate) {
      this.predicate = predicate;
      this.operatorPredicate = operatorPredicate;
    }
  }

}
