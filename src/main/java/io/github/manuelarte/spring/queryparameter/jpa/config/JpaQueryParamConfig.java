package io.github.manuelarte.spring.queryparameter.jpa.config;

import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaConfig;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultGreaterThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultGreaterThanPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultInPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultLowerThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultLowerThanPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.OperatorPredicate;
import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.InOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQueryProvider;
import io.github.manuelarte.spring.queryparameter.operators.queryprovider.OperatorQueryProviderImpl;
import io.github.manuelarte.spring.queryparameter.util.TriPredicate;
import javax.persistence.criteria.Predicate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({QueryCriteriaConfig.class})
public class JpaQueryParamConfig {

  @Bean
  @ConditionalOnMissingBean
  public OperatorQueryProvider<OperatorPredicate<Object>, Predicate> operatorsPredicateProvider() {
    final OperatorQueryProvider<OperatorPredicate<Object>, Predicate> impl = new OperatorQueryProviderImpl<>();
    impl.addOperatorQuerySelector(
        isOperator(EqualsOperator.class), new DefaultEqualsPredicate<>());
    impl.addOperatorQuerySelector(
        isOperator(GreaterThanOperator.class), new DefaultGreaterThanPredicate<>());
    impl.addOperatorQuerySelector(isOperator(
        GreaterThanOrEqualsOperator.class), new DefaultGreaterThanOrEqualsPredicate<>());
    impl.addOperatorQuerySelector(
        isOperator(LowerThanOperator.class), new DefaultLowerThanPredicate<>());
    impl.addOperatorQuerySelector(isOperator(
        LowerThanOrEqualsOperator.class), new DefaultLowerThanOrEqualsPredicate<>());
    impl.addOperatorQuerySelector(isOperator(
        InOperator.class), new DefaultInPredicate<>());
    return impl;
  }

  private TriPredicate<Class<?>, String, Operator<Object>> isOperator(
      Class<? extends Operator> operatorClass) {
    return (x, y, z) -> z.getClass().equals(operatorClass);
  }

}
