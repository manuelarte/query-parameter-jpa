package io.github.manuelarte.spring.queryparameter.jpa;

import io.github.manuelarte.spring.queryparameter.config.WebMvcConfig;
import io.github.manuelarte.spring.queryparameter.jpa.config.JpaQueryParamConfig;
import io.github.manuelarte.spring.queryparameter.model.QueryParameterArgumentResolver;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerRegistry;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ WebMvcConfig.class, QueryParameterArgumentResolver.class,
    TypeTransformerRegistry.class, JpaQueryParamConfig.class})
@SuppressWarnings("unused")
public @interface EnableQueryParameter {

}
