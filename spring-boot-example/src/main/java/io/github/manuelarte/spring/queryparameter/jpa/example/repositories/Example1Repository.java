package io.github.manuelarte.spring.queryparameter.jpa.example.repositories;

import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Example1Repository extends JpaRepository<Example1, Long>, JpaSpecificationExecutor<Example1> {

}
