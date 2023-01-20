package com.home.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debit-client")
public class Controller {
    @Value("${eureka.instance.instance-id}")
    String instance;

    @GetMapping("/test")
    public String test() {
        return "Hello on " + instance;
    }
}
