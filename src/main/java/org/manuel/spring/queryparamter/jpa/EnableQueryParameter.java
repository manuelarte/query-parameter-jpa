package org.manuel.spring.queryparamter.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.manuel.spring.queryparameter.config.QueryCriteriaConfig;
import org.manuel.spring.queryparameter.operators.EqualsOperator;
import org.manuel.spring.queryparameter.operators.GreaterThanOperator;
import org.manuel.spring.queryparameter.operators.GreaterThanOrEqualsOperator;
import org.manuel.spring.queryparameter.operators.LowerThanOperator;
import org.manuel.spring.queryparameter.operators.LowerThanOrEqualsOperator;
import org.manuel.spring.queryparamter.jpa.config.JpaQueryParamConfig;
import org.manuel.spring.queryparamter.jpa.config.WebMvcConfig;
import org.manuel.spring.queryparamter.jpa.model.QueryParameterArgumentResolver;
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
