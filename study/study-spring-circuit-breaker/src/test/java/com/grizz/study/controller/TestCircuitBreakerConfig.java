package com.grizz.study.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class TestCircuitBreakerConfig {
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        Customizer<CircuitBreaker> eventLogger = Customizer.once(circuitBreaker -> {
            circuitBreaker.getEventPublisher()
                    .onSuccess(event ->
                            log.info("Circuit breaker {} success", event.getCircuitBreakerName()))
                    .onError(event ->
                            log.info("Circuit breaker {} error {}",
                                    event.getCircuitBreakerName(),
                                    event.getThrowable().getMessage()))
                    .onStateTransition(event ->
                            log.info("Circuit breaker {} state changed from {} to {}",
                                    event.getCircuitBreakerName(),
                                    event.getStateTransition().getFromState(),
                                    event.getStateTransition().getToState()))
                    .onSlowCallRateExceeded(event ->
                            log.info("Circuit breaker {} slow call rate exceeded", event.getCircuitBreakerName()))
                    .onFailureRateExceeded(event ->
                            log.info("Circuit breaker {} failure rate exceeded", event.getCircuitBreakerName()));

        }, CircuitBreaker::getName);

        var config = CircuitBreakerConfig.custom()
                .slidingWindowSize(4)
                .build();

        return factory -> {
            factory.configureDefault(id -> {
                factory.addCircuitBreakerCustomizer(eventLogger, id);

                return new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(config)
                        .build();
            });
        };
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> autoTransitionCustomizer() {
        Customizer<CircuitBreaker> eventLogger = Customizer.once(circuitBreaker -> {
            circuitBreaker.getEventPublisher()
                    .onSuccess(event ->
                            log.info("Circuit breaker {} success", event.getCircuitBreakerName()))
                    .onError(event ->
                            log.info("Circuit breaker {} error {}",
                                    event.getCircuitBreakerName(),
                                    event.getThrowable().getMessage()))
                    .onStateTransition(event ->
                            log.info("Circuit breaker {} state changed from {} to {}",
                                    event.getCircuitBreakerName(),
                                    event.getStateTransition().getFromState(),
                                    event.getStateTransition().getToState()))
                    .onSlowCallRateExceeded(event ->
                            log.info("Circuit breaker {} slow call rate exceeded", event.getCircuitBreakerName()))
                    .onFailureRateExceeded(event ->
                            log.info("Circuit breaker {} failure rate exceeded {}",
                                    event.getCircuitBreakerName(),
                                    event.getFailureRate()));
        }, CircuitBreaker::getName);

        var config = CircuitBreakerConfig.custom()
                .slidingWindowSize(4)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .waitDurationInOpenState(Duration.ofSeconds(2))
                .permittedNumberOfCallsInHalfOpenState(6)
                .build();

        return factory -> {
            factory.addCircuitBreakerCustomizer(eventLogger, "halfOpenSuccess", "halfOpenFailed");
            factory.configure(builder -> {
                builder.circuitBreakerConfig(config)
                        .build();
            }, "halfOpenSuccess", "halfOpenFailed");
        };
    }
}
