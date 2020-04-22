package io.github.manuelarte.spring.queryparameter.jpa.example.repositories;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ParentRepository extends JpaRepository<Parent, Long>, JpaSpecificationExecutor<Parent> {

}
