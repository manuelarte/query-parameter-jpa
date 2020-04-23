package io.github.manuelarte.spring.queryparameter.jpa.config;

import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaConfig;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultGreaterThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultGreaterThanPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultInPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultLowerThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultLowerThanPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultNotEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultNotGreaterThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultNotGreaterThanPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultNotInPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultNotLowerThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultNotLowerThanPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.transformers.QueryCriteriaToSpecificationTransformer;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerProvider;
import io.github.manuelarte.spring.queryparameter.operators.AbstractMiddleOperator;
import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.InOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.NotAbstractMiddleOperatorOperator;
import io.github.manuelarte.spring.queryparameter.operators.NotInOperator;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQueryProvider;
import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQueryProviderImpl;
import io.github.manuelarte.spring.queryparameter.transformers.QueryCriteriaTransformer;
import io.github.manuelarte.spring.queryparameter.util.TriPredicate;
import javax.persistence.criteria.Predicate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;

@Configuration
@Import({QueryCriteriaConfig.class})
public class JpaQueryParamConfig {

  @Bean
  @ConditionalOnMissingBean
  public QueryCriteriaTransformer<Specification<Object>> queryCriteriaToSpecificationTransformer(
      final TypeTransformerProvider typeTransformerProvider,
      final OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorPredicateProvider) {
    return new QueryCriteriaToSpecificationTransformer(typeTransformerProvider, operatorPredicateProvider);
  }

  @Bean
  @ConditionalOnMissingBean
  public OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorsPredicateProvider() {
    final OperatorQueryProvider<OperatorPredicate<Object>, Predicate> impl = new OperatorQueryProviderImpl<>();
    impl.addOperatorQuerySelector(
        isOperator(EqualsOperator.class), new DefaultEqualsPredicate<>());
    impl.addOperatorQuerySelector(
        isNotOperator(EqualsOperator.class), new DefaultNotEqualsPredicate<>()
    );
    impl.addOperatorQuerySelector(
        isOperator(GreaterThanOperator.class), new DefaultGreaterThanPredicate<>());
    impl.addOperatorQuerySelector(
        isNotOperator(GreaterThanOperator.class), new DefaultNotGreaterThanPredicate<>()
    );

    impl.addOperatorQuerySelector(isOperator(
        GreaterThanOrEqualsOperator.class), new DefaultGreaterThanOrEqualsPredicate<>());
    impl.addOperatorQuerySelector(
        isNotOperator(GreaterThanOrEqualsOperator.class), new DefaultNotGreaterThanOrEqualsPredicate<>()
    );

    impl.addOperatorQuerySelector(
        isOperator(LowerThanOperator.class), new DefaultLowerThanPredicate<>());
    impl.addOperatorQuerySelector(
        isNotOperator(LowerThanOperator.class), new DefaultNotLowerThanPredicate<>()
    );

    impl.addOperatorQuerySelector(isOperator(
        LowerThanOrEqualsOperator.class), new DefaultLowerThanOrEqualsPredicate<>());
    impl.addOperatorQuerySelector(
        isNotOperator(LowerThanOrEqualsOperator.class), new DefaultNotLowerThanOrEqualsPredicate<>()
    );

    impl.addOperatorQuerySelector(isOperator(
        InOperator.class), new DefaultInPredicate<>());
    impl.addOperatorQuerySelector(isOperator(
        NotInOperator.class), new DefaultNotInPredicate());
    return impl;
  }

  private TriPredicate<Class<?>, String, Operator<Object>> isOperator(
      Class<? extends Operator> operatorClass) {
    return (x, y, z) -> z.getClass().equals(operatorClass);
  }

  private TriPredicate<Class<?>, String, Operator<Object>> isNotOperator(
      final Class<? extends AbstractMiddleOperator> abstractMiddleOperator) {
    return (x, y, z) -> {
      if (NotAbstractMiddleOperatorOperator.class.isInstance(z)) {
        final NotAbstractMiddleOperatorOperator casted = NotAbstractMiddleOperatorOperator.class.cast(z);
        return casted.getAbstractMiddleOperator().getClass().equals(abstractMiddleOperator);
      }
      return false;
    };
  }

}
