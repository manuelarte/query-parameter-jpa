package io.github.manuelarte.spring.queryparameter.jpa.example.config;

import com.github.javafaker.Faker;
import io.github.manuelarte.spring.queryparameter.jpa.example.model.entities.Example1;
import io.github.manuelarte.spring.queryparameter.jpa.example.repositories.Example1Repository;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class TestData {

  private final Example1Repository example1Repository;

  @EventListener(ApplicationReadyEvent.class)
  public void populateTestData() {
    final List<Example1> example1s = example1Repository
        .saveAll(
            IntStream.range(0, 4).mapToObj(it -> createFakeExample1()).collect(Collectors.toList()));
  }

  private Example1 createFakeExample1() {
    final Faker faker = new Faker();
    return Example1.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .age(new Random().nextInt(100))
        .build();
  }

}
