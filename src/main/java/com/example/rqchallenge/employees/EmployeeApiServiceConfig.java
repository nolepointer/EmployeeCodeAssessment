package com.example.rqchallenge.employees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmployeeApiServiceConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeApiServiceConfig.class);

    private static final String MOCK_EMPLOYEE_API_KEY = "mockEmployeeApi";

    @Bean
    @ConditionalOnProperty(name = MOCK_EMPLOYEE_API_KEY, havingValue = "false")
    public EmployeeApiService dummyApiService(WebClient.Builder webClientBuilder) {
        logger.info("Configuring DummyApiService");

        return new DummyApiService(webClientBuilder);
    }

    @Bean
    @ConditionalOnProperty(name = MOCK_EMPLOYEE_API_KEY, havingValue = "true")
    public EmployeeApiService mockEmployeeApiService() {
        logger.info("Configuring MockEmployeeApiService");

        return new MockEmployeeApiService();
    }
}
