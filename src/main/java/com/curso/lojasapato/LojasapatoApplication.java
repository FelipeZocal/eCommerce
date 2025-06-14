package com.curso.lojasapato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.curso.domains"})
@EnableJpaRepositories(basePackages = "com.curso.repositories")
@ComponentScan(basePackages = "com.curso")
public class LojasapatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojasapatoApplication.class, args);
	}

}
