package io.github.manuelarte.spring.queryparameter.jpa.example.services;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Parent;
import io.github.manuelarte.spring.queryparameter.jpa.example.repositories.ParentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@lombok.AllArgsConstructor
class ParentServiceImpl implements ParentService {

  private final ParentRepository parentRepository;

  @Override
  public Page<Parent> findAll(final Pageable pageable) {
    return parentRepository.findAll(pageable);
  }

  @Override
  public Page<Parent> findAll(final Specification<Parent> specification, final Pageable pageable) {
    return parentRepository.findAll(specification, pageable);
  }

  @Override
  public Parent save(final Parent parent) {
    return parentRepository.save(parent);
  }
}
