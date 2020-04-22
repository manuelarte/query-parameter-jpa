package io.github.manuelarte.spring.queryparameter.jpa.example.services;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface Example1Service {

  Page<Example1> findAll(Pageable pageable);

  Page<Example1> findAll(Specification<Example1> specification, Pageable pageable);

  Example1 save(Example1 example1);

}
