package io.github.manuelarte.spring.queryparameter.jpa;

import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaConfig;
import io.github.manuelarte.spring.queryparameter.jpa.config.JpaQueryParamConfig;
import io.github.manuelarte.spring.queryparameter.jpa.config.WebMvcConfig;
import io.github.manuelarte.spring.queryparameter.jpa.model.QueryParameterArgumentResolver;
import io.github.manuelarte.spring.queryparameter.operators.EqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOperator;
import io.github.manuelarte.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({EqualsOperator.class, GreaterThanOrEqualsOperator.class, GreaterThanOperator.class,
    LowerThanOrEqualsOperator.class, LowerThanOperator.class,
    QueryCriteriaConfig.class,
    QueryParameterArgumentResolver.class,
    WebMvcConfig.class,
    JpaQueryParamConfig.class})
@SuppressWarnings("unused")
public @interface EnableQueryParameter {

}