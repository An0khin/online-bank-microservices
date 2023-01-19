package com.home.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringConfig {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }
}
