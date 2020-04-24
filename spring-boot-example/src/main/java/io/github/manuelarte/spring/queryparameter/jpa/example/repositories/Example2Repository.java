package io.github.manuelarte.spring.queryparameter.jpa.example.repositories;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example1;
import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Example2Repository extends JpaRepository<Example2, Long>, JpaSpecificationExecutor<Example2> {

}
