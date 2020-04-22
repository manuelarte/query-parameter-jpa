package io.github.manuelarte.spring.queryparameter.jpa.example.model.entities;

import io.github.manuelarte.spring.queryparameter.query.constraints.QueryParamNotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@lombok.Builder(toBuilder = true)
public class Example2 {

  @Id
  @GeneratedValue
  private Long id;
  @QueryParamNotNull
  private String firstName;
  private String lastName;
  private int age;

}
