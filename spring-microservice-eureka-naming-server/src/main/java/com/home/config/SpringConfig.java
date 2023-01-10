package com.home.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
@EnableEurekaServer
public class SpringConfig implements WebMvcConfigurer {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        registry.viewResolver(thymeleafViewResolver);
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();

        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(resourceTemplateResolver());

        return engine;
    }

    @Bean
    public SpringResourceTemplateResolver resourceTemplateResolver() {
        SpringResourceTemplateResolver resourceTemplateResolver = new SpringResourceTemplateResolver();

        resourceTemplateResolver.setApplicationContext(applicationContext);
        resourceTemplateResolver.setPrefix("/WEB-INF/views");
        resourceTemplateResolver.setSuffix(".html");

        return resourceTemplateResolver;
    }
}
