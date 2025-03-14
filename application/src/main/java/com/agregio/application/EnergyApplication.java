package com.agregio.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(
        {
                "com.agregio.adaptersinput",
                "com.agregio.applicationservices",
                "com.agregio.adaptersoutput",
                "com.agregio.domain"
        }
)
@EnableJpaRepositories("com.agregio.adaptersoutput.repositories")
@EntityScan("com.agregio.adaptersoutput.entities")
public class EnergyApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyApplication.class, args);
    }
}