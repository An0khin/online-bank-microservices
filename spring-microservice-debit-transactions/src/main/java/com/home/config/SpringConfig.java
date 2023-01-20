package com.home.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.home.*")
@EntityScan("com.home.*")
@EnableJpaRepositories(basePackages = "com.home.repository")
public class SpringConfig {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }
}
