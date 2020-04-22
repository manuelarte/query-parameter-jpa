package io.github.manuelarte.spring.queryparameter.jpa.example.services;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ParentService  {

  Page<Parent> findAll(Pageable pageable);

  Page<Parent> findAll(Specification<Parent> specification, Pageable pageable);

  Parent save(Parent parent);

}
