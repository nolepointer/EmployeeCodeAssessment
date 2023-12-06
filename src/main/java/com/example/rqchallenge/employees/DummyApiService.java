package com.example.rqchallenge.employees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClient;

import exceptions.ExternalApiException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DummyApiService implements EmployeeApiService {

    private static final Logger logger = LoggerFactory.getLogger(DummyApiService.class);
    private final WebClient webClient;
    private static final String API_BASE_URL = "https://dummy.restapiexample.com/api/v1";

    public DummyApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_BASE_URL).build();
        logger.info("DummyApiService initialized with base URL: {}", API_BASE_URL);
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");

        DummyApiWrapperDto<DummyEmployeeDto[]> wrapper = webClient.get()
                .uri("/employees")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DummyApiWrapperDto<DummyEmployeeDto[]>>() {
                })
                .block();

        checkApiResponseStatus(wrapper);

        return Arrays.stream(wrapper.getData())
                .map(EmployeeTransformer::transform)
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(@NonNull String id) {
        logger.info("Fetching employee with ID: {}", id);

        DummyApiWrapperDto<DummyEmployeeDto> wrapper = webClient.get()
                .uri("/employee/{id}", id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DummyApiWrapperDto<DummyEmployeeDto>>() {
                })
                .block();

        checkApiResponseStatus(wrapper);
        return EmployeeTransformer.transform(wrapper.getData());
    }

    @Override
    public Employee createEmployee(@NonNull Map<String, Object> employeeInput) {
        logger.info("Creating employee");

        DummyApiWrapperDto<DummyEmployeeDto> wrapper = webClient.post()
                .uri("/create")
                .body(Mono.just(employeeInput), Map.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DummyApiWrapperDto<DummyEmployeeDto>>() {
                })
                .block();

        checkApiResponseStatus(wrapper);
        return getEmployeeById(wrapper.getData().getId());
    }

    @Override
    public String deleteEmployee(@NonNull String id) {
        logger.info("Deleting employee with ID: {}", id);

        DummyApiWrapperDto<Object> wrapper = webClient.delete()
                .uri("/delete/{id}", id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DummyApiWrapperDto<Object>>() {
                })
                .block();

        checkApiResponseStatus(wrapper);
        return id;
    }

    private void checkApiResponseStatus(DummyApiWrapperDto wrapper) {
        
        if (wrapper == null || !"success".equals(wrapper.getStatus())) {
            logger.error("External API returned an error with message: ", wrapper.getMessage());
            throw new ExternalApiException("External API returned an error");
        }
    }
}
