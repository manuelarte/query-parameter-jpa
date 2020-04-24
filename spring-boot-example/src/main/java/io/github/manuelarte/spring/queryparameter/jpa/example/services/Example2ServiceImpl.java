package io.github.manuelarte.spring.queryparameter.jpa.example.services;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example2;
import io.github.manuelarte.spring.queryparameter.jpa.example.repositories.Example2Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class Example2ServiceImpl implements Example2Service {

  private final Example2Repository example2Repository;

  @Override
  public Page<Example2> findAll(final Pageable pageable) {
    return example2Repository.findAll(pageable);
  }

  @Override
  public Page<Example2> findAll(final Specification<Example2> specification, final Pageable pageable) {
    return example2Repository.findAll(specification, pageable);
  }

  @Override
  public Example2 save(final Example2 example1) {
    return example2Repository.save(example1);
  }
}
