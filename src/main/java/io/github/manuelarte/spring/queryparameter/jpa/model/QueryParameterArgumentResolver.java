package io.github.manuelarte.spring.queryparameter.jpa.model;

import io.github.manuelarte.spring.queryparameter.config.QueryCriteriaParser;
import io.github.manuelarte.spring.queryparameter.exceptions.QueryParserException;
import io.github.manuelarte.spring.queryparameter.jpa.QueryParameter;
import io.github.manuelarte.spring.queryparameter.model.QueryCriteriaParserContext;
import io.github.manuelarte.spring.queryparameter.model.TypeTransformerProvider;
import io.github.manuelarte.spring.queryparameter.query.QueryCriteria;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
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
  private final OperatorPredicateProvider operatorPredicateProvider;

  public QueryParameterArgumentResolver(final QueryCriteriaParser queryCriteriaParser,
      final TypeTransformerProvider typeTransformerProvider,
      final OperatorPredicateProvider operatorPredicateProvider) {
    this.queryCriteriaParser = queryCriteriaParser;
    this.typeTransformerProvider = typeTransformerProvider;
    this.operatorPredicateProvider = operatorPredicateProvider;
  }

  @Override
  public boolean supportsParameter(final MethodParameter parameter) {
    return parameter.getParameterAnnotation(QueryParameter.class) != null;
  }

  @Override
  @SuppressWarnings("GetClassOnClass")
  public Object resolveArgument(final MethodParameter parameter,
      final ModelAndViewContainer mavContainer,
      final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
    final QueryParameter queryParameter = parameter.getParameterAnnotation(QueryParameter.class);
    final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    final String q = request.getParameter(queryParameter.paramName());
    if (q != null && !q.isEmpty()) {
      final QueryCriteriaParserContext context = createContext(queryParameter);
      final QueryCriteria queryCriteria = queryCriteriaParser.parse(q, context);
      final Specification<?> specification =
          new QueryCriteriaJpaSpecification<>(
              queryParameter.entity(), queryCriteria, typeTransformerProvider,
              operatorPredicateProvider);
      if (parameter.getParameterType().equals(Specification.class)) {
        return specification;
      } else if (isValidOptional(parameter)) {
        return Optional.of(specification);
      } else {
        throw new QueryParserException("Don't know how to parse to the class "
            + parameter.getParameterType().getClass());
      }
    } else {
      if (parameter.getParameterType().equals(Specification.class)) {
        return null;
      } else if (isValidOptional(parameter)) {
        return Optional.empty();
      } else {
        throw new QueryParserException("Don't know how to parse to the class "
            + parameter.getParameterType().getClass());
      }
    }
  }

  private QueryCriteriaParserContext createContext(final QueryParameter annotation) {
    final Set<String> allowedKeys =
        annotation.allowedKeys().length > 0
            ? new HashSet<>(Arrays.asList(annotation.allowedKeys()))
            : null;
    final Set<String> notAllowedKeys =
        annotation.notAllowedKeys().length > 0
            ? new HashSet<>(Arrays.asList(annotation.notAllowedKeys()))
            : null;
    return new QueryCriteriaParserContext(allowedKeys, notAllowedKeys);
  }

  private boolean isValidOptional(final MethodParameter parameter) {
    return (parameter.getParameterType().equals(Optional.class)
        && parameter.getGenericParameterType() instanceof ParameterizedType
        && Specification.class.equals(((ParameterizedType) parameter.getGenericParameterType())
        .getActualTypeArguments()[0]));
  }

}
