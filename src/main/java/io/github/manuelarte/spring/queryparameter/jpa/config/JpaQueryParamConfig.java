package io.github.manuelarte.spring.queryparameter.jpa.config;

import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaConfig;
import io.github.manuelarte.spring.queryparameter.jpa.model.OperatorPredicateProvider;
import io.github.manuelarte.spring.queryparameter.jpa.model.OperatorPredicateProviderImpl;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultGreaterThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultGreaterThanPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultInPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultLowerThanOrEqualsPredicate;
import io.github.manuelarte.spring.queryparameter.jpa.operatorpredicate.DefaultLowerThanPredicate;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerProvider;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;
import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.InOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.Operator;
import io.github.manuelarte.spring.queryparameter.transformers.ClassFieldTransformerImpl;
import io.github.manuelarte.spring.queryparameter.transformers.TypeTransformer;
import io.github.manuelarte.spring.queryparameter.util.TriPredicate;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;

@Configuration
@Import({QueryCriteriaConfig.class, TypeTransformerRegistry.class})
public class JpaQueryParamConfig {

  private final List<QueryParameterConfig> queryParameterConfigs;

  public JpaQueryParamConfig(final List<QueryParameterConfig> queryParameterConfigs) {
    this.queryParameterConfigs = queryParameterConfigs;
  }

  @Bean("defaultTypeTransformer")
  public TypeTransformer defaultTypeTransformer(final ConversionService conversionService) {
    return new ClassFieldTransformerImpl(conversionService);
  }

  @Bean
  @ConditionalOnMissingBean
  public TypeTransformerProvider typeTransformerProvider(
      @Qualifier("defaultTypeTransformer") final TypeTransformer typeTransformer,
      final TypeTransformerRegistry typeTransformerRegistry) {
    queryParameterConfigs.forEach(it -> it.addTypeTransformer(typeTransformerRegistry));
    final TypeTransformerProvider typeTransformerProvider =
        new TypeTransformerProvider(typeTransformerRegistry, typeTransformer);
    return typeTransformerProvider;
  }

  @Bean
  @ConditionalOnMissingBean
  public OperatorPredicateProvider operatorsPredicateProvider() {
    final OperatorPredicateProvider impl = new OperatorPredicateProviderImpl();
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
    impl.addOperatorPredicateSelector(isOperator(
        InOperator.class), new DefaultInPredicate<>());
    return impl;
  }

  private TriPredicate<Class<?>, String, Operator<Object>> isOperator(
      Class<? extends Operator> operatorClass) {
    return (x, y, z) -> z.getClass().equals(operatorClass);
  }

}
