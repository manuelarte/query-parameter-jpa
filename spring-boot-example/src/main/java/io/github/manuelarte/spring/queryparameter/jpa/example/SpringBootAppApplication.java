package io.github.manuelarte.spring.queryparameter.jpa.example;

import io.github.manuelarte.spring.queryparameter.jpa.EnableQueryParameter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableQueryParameter
public class SpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppApplication.class, args);
	}

}
