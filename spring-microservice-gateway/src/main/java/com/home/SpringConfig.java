package com.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringConfig {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("debit-route", r -> r.path("/debit/**")
                        .uri("lb://debit-client"))
                .route("saving-route", r -> r.path("/saving/**")
                        .uri("lb://saving-client"))
                .route("register", r -> r.path("/register")
                        .filters(spec -> spec.rewritePath("/register", "/auth"))
                        .uri("http://localhost:8086"))
                .route("token", r -> r.path("/login")
                        .filters(spec -> spec.rewritePath("/login", "/auth/token"))
                        .uri("http://localhost:8086"))
                .build();
    }
}
