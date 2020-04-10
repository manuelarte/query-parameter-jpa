package org.manuel.spring.queryparamter.jpa.config;

import org.manuel.spring.queryparameter.config.QueryCriteriaConfig;
import org.manuel.spring.queryparameter.config.QueryCriteriaParser;
import org.manuel.spring.queryparameter.config.QueryCriteriaParserImpl;
import org.manuel.spring.queryparameter.operators.EqualsOperator;
import org.manuel.spring.queryparameter.operators.GreaterThanOperator;
import org.manuel.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import org.manuel.spring.queryparameter.operators.LowerThanOperator;
import org.manuel.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import org.manuel.spring.queryparameter.operators.Operator;
import org.manuel.spring.queryparameter.transformers.ClassFieldTransformerImpl;
import org.manuel.spring.queryparameter.transformers.TypeTransformer;
import org.manuel.spring.queryparameter.transformers.TypeTransformerProvider;
import org.manuel.spring.queryparamter.jpa.model.OperatorsPredicateProvider;
import org.manuel.spring.queryparamter.jpa.model.OperatorsPredicateProviderImpl;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.DefaultEqualsPredicate;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.DefaultGreaterThanOrEqualsPredicate;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.DefaultGreaterThanPredicate;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.DefaultLowerThanOrEqualsPredicate;
import org.manuel.spring.queryparamter.jpa.operatorpredicate.DefaultLowerThanPredicate;
import org.manuel.spring.queryparamter.jpa.util.TriPredicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;

@Configuration
@Import(QueryCriteriaConfig.class)
public class JpaQueryParamConfig {

  @Bean("defaultTypeTransformer")
  @ConditionalOnMissingBean
  public TypeTransformer defaultTypeTransformer(final ConversionService conversionService) {
    return new ClassFieldTransformerImpl(conversionService);
  }

  @Bean
  @ConditionalOnMissingBean
  public TypeTransformerProvider typeTransformerProvider(
      @Qualifier("defaultTypeTransformer") TypeTransformer typeTransformer) {
    return new TypeTransformerProvider(typeTransformer);
  }

  @Bean
  @ConditionalOnMissingBean
  public OperatorsPredicateProvider operatorsPredicateProvider() {
    final OperatorsPredicateProvider impl = new OperatorsPredicateProviderImpl();
    impl.addOperatorPredicateSelector(
        isOperator(EqualsOperator.class), new DefaultEqualsPredicate<>());
    impl.addOperatorPredicateSelector(
        isOperator(GreaterThanOperator.class), new DefaultGreaterThanPredicate<>());
    impl.addOperatorPredicateSelector(isOperator(
        GreaterThanOrEqualsOperator.class), new DefaultGreaterThanOrEqualsPredicate<>());
    impl.addOperatorPredicateSelector(
        isOperator(LowerThanOperator.class), new DefaultLowerThanPredicate<>());
    impl.addOperatorPredicateSelector(isOperator(
        LowerThanOrEqualsOperator.class), new DefaultLowerThanOrEqualsPredicate<>());
    return impl;
  }

  private TriPredicate<Class<?>, String, Operator<Object>> isOperator(
      Class<? extends Operator> operatorClass) {
    return (x, y, z) -> z.getClass().equals(operatorClass);
  }

}
