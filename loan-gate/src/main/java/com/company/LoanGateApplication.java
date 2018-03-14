package com.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 */
@Slf4j
// TODO Actually need to specify a more sophisticated cache implementation (with bunch of settings) vs to default Spring Boot implementation of Cache
@EnableCaching
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.company"})
@EntityScan(basePackages = {"com.company.model"})
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class LoanGateApplication {

    public static void main(String... args) {
        SpringApplication.run(LoanGateApplication.class, args);
    }

}
