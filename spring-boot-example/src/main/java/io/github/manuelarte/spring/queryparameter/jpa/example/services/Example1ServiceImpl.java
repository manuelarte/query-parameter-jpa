package io.github.manuelarte.spring.queryparameter.jpa.example.services;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example1;
import io.github.manuelarte.spring.queryparameter.jpa.example.repositories.Example1Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class Example1ServiceImpl implements Example1Service {

  private final Example1Repository example1Repository;

  @Override
  public Page<Example1> findAll(final Pageable pageable) {
    return example1Repository.findAll(pageable);
  }

  @Override
  public Page<Example1> findAll(final Specification<Example1> specification, final Pageable pageable) {
    return example1Repository.findAll(specification, pageable);
  }

  @Override
  public Example1 save(final Example1 example1) {
    return example1Repository.save(example1);
  }
}
