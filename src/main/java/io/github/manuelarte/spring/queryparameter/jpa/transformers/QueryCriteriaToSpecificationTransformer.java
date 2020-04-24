package io.github.manuelarte.spring.queryparameter.jpa.transformers;

import io.github.manuelarte.spring.queryparameter.jpa.model.QueryCriteriaJpaSpecification;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerProvider;
import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQueryProvider;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import io.github.manuelarte.spring.queryparameter.transformers.QueryCriteriaTransformer;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class QueryCriteriaToSpecificationTransformer implements QueryCriteriaTransformer<Specification<Object>> {

  private final TypeTransformerProvider typeTransformerProvider;
  private final OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorPredicateProvider;

  public QueryCriteriaToSpecificationTransformer(
      final TypeTransformerProvider typeTransformerProvider,
      final OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorPredicateProvider) {
    this.typeTransformerProvider = typeTransformerProvider;
    this.operatorPredicateProvider = operatorPredicateProvider;
  }

  @Override
  public boolean supports(final Class<?> clazz) {
    return Specification.class.equals(clazz);
  }

  @Override
  public Specification<Object> apply(final Class<?> entity, final QueryCriteria queryCriteria) {
    return new QueryCriteriaJpaSpecification(entity, queryCriteria, typeTransformerProvider, operatorPredicateProvider);
  }
}
