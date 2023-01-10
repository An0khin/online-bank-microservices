package com.home.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringJUnitConfig(classes = SpringConfig.class)
@WebAppConfiguration
public class SpringApplicationTest {
    @Test
    public void contextLoads() {

    }
}
