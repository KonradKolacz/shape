package com.example.final_test.config;

import com.example.final_test.repository.CustomAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    @Bean
    public CustomAuditorAware auditorProvider() {
        return new CustomAuditorAware();
    }

}
