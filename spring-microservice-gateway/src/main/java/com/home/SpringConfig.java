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
                .route("credit-route", r -> r.path("/credit/**")
                        .uri("lb://credit-client"))
//                .route("register", r -> r.path("/register")
////                        .filters(spec -> spec.rewritePath("/register", "/auth"))
//                        .uri("lb://account-client"))
                .route("registerRest", r -> r.path("/security/register")
                        .filters(spec -> spec.rewritePath("/security/register", "/auth"))
                        .uri("lb://security-client"))
                .route("token", r -> r.path("/account/login")
                        .filters(spec -> spec.rewritePath("/account/login", "/auth/token"))
                        .uri("lb://security-client"))
                .route("new_token", r -> r.path("/auth/**")
                        .uri("lb://security-client"))
                .route("account-route", r -> r.path("/account/**")
                        .uri("lb://account-client"))
                .build();
    }
}
