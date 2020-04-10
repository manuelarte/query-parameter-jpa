package io.github.manuelarte.spring.queryparameter.jpa.model;

import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.util.TriPredicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperatorPredicateProviderImpl implements OperatorPredicateProvider {

  private final List<OperatorPredicateEntry> operatorPredicateEntries;

  public OperatorPredicateProviderImpl() {
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
