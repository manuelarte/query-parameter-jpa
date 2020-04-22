package io.github.manuelarte.spring.queryparameter.jpa.example.services;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface Example2Service {

  Page<Example2> findAll(Pageable pageable);

  Page<Example2> findAll(Specification<Example2> specification, Pageable pageable);

  Example2 save(Example2 example1);

}
