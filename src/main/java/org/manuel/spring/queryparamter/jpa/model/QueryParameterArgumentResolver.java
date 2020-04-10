package org.manuel.spring.queryparamter.jpa.model;

import javax.servlet.http.HttpServletRequest;
import org.manuel.spring.queryparameter.config.QueryCriteriaParser;
import org.manuel.spring.queryparameter.query.QueryCriteria;
import org.manuel.spring.queryparameter.transformers.TypeTransformerProvider;
import org.manuel.spring.queryparamter.jpa.QueryParameter;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class QueryParameterArgumentResolver implements HandlerMethodArgumentResolver {

  private final QueryCriteriaParser queryCriteriaParser;
  private final TypeTransformerProvider typeTransformerProvider;
  private final OperatorsPredicateProvider operatorsPredicateProvider;

  public QueryParameterArgumentResolver(final QueryCriteriaParser queryCriteriaParser,
      final TypeTransformerProvider typeTransformerProvider,
      final OperatorsPredicateProvider operatorsPredicateProvider) {
    this.queryCriteriaParser = queryCriteriaParser;
    this.typeTransformerProvider = typeTransformerProvider;
    this.operatorsPredicateProvider = operatorsPredicateProvider;
  }

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return parameter.getParameterAnnotation(QueryParameter.class) != null;
  }

  @Override
  public Object resolveArgument(final MethodParameter parameter,
      final ModelAndViewContainer mavContainer,
      final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
    final QueryParameter queryParameter = parameter.getParameterAnnotation(QueryParameter.class);
    final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    final String q = request.getParameter(queryParameter.paramName());
    if (q != null && !q.isEmpty()) {
      final QueryCriteria queryCriteria = queryCriteriaParser.parse(q);
      final Specification<?> specification =
          new QueryCriteriaJpaSpecification<>(
              queryParameter.entity(), queryCriteria, typeTransformerProvider,
              operatorsPredicateProvider);
      return specification;
    } else {
      return null;
    }
  }
}
