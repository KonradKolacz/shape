package com.example.final_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FinalTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalTestApplication.class, args);
    }

}
