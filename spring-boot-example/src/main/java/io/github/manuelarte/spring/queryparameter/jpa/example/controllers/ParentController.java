package io.github.manuelarte.spring.queryparameter.jpa.example.controllers;

import io.github.manuelarte.spring.queryparameter.QueryParameter;
import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Parent;
import io.github.manuelarte.spring.queryparameter.jpa.example.services.ParentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parents")
@lombok.AllArgsConstructor
public class ParentController {

  private final ParentService parentService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Parent>> findByPage(
      @PageableDefault final Pageable pageable,
      @QueryParameter(entity = Parent.class) final Specification<Parent> queryCriteria) {
    final Page<Parent> page;
    if (queryCriteria != null) {
      page = parentService.findAll(queryCriteria, pageable);
    } else {
      page = parentService.findAll(pageable);
    }
    return ResponseEntity.ok(page);
  }

}
