package com.company.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import static com.company.Constants.*;

/**
 *
 */
@Slf4j
@Configuration
@ConfigurationProperties
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value(value = "${loan.gate.rest.connection.connectionRequestTimeout:10000}")
    private int restConnectionRequestTimeout;

    @Value(value = "${loan.gate.rest.connection.connectionTimeout:10000}")
    private int restConnectionTimeout;

    @Autowired
    @Qualifier(value = "validationInterceptor")
    HandlerInterceptor validationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validationInterceptor).addPathPatterns(ROOT_PATH + "/" + VERSION + "/**");
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory restHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setConnectTimeout(restConnectionTimeout);
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(restConnectionRequestTimeout);
        return httpComponentsClientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(restHttpRequestFactory());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(mapper()));
    }

    @Bean(name = "objectMapper")
    public ObjectMapper mapper(){
        return mapperBuilder().build();
    }

    public Jackson2ObjectMapperBuilder mapperBuilder(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.indentOutput(true);
        builder.dateFormat(DATE_TIME_FORMATTER);
        return builder;
    }

}
