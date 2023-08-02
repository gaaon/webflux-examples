package com.grizz.wooman.rms.apigateway.filter;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

@Slf4j
@TestConfiguration
public class TestScgCircuitBreakerConfig {
    private Customizer<CircuitBreaker> getEventLogger() {
        return Customizer.once(circuitBreaker -> {
            var cbName = circuitBreaker.getName();
            circuitBreaker.getEventPublisher()
                    .onSuccess(event ->
                            log.info("[{}] success", cbName))
                    .onError(event ->
                            log.info("[{}] error: {}", cbName,
                            event.getThrowable().toString()))
                    .onStateTransition(event -> {
                            log.info("[{}] state changed from {} to {}",
                                    cbName,
                                    event.getStateTransition().getFromState(),
                                    event.getStateTransition().getToState());
                    })
                    .onSlowCallRateExceeded(event ->
                            log.info("[{}] slow call rate exceeded: {}",
                                    cbName, event.getSlowCallRate()))
                    .onFailureRateExceeded(event ->
                            log.info("[{}] failure rate exceeded: {}",
                                    cbName, event.getFailureRate()));

        }, CircuitBreaker::getName);
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> halfOpen() {
        var targets = new String[]{"halfOpen"};
        return factory -> {
            factory.addCircuitBreakerCustomizer(
                    getEventLogger(), targets);
        };
    }
}
