package org.manuel.spring.queryparamter.jpa.config;

import java.util.List;
import org.manuel.spring.queryparamter.jpa.model.QueryParameterArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Lazy
  @Autowired
  private QueryParameterArgumentResolver queryParameterArgumentResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(queryParameterArgumentResolver);
  }

}
