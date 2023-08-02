package com.grizz.wooman.rms.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@Slf4j
@TestConfiguration
public class TestCircuitBreakerConfig {
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
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> {
            factory.configureDefault(id -> {
                factory.addCircuitBreakerCustomizer(getEventLogger(), id);

                return new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(
                                CircuitBreakerConfig.ofDefaults()
                        ).build();
            });
        };
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> miniCustomizer() {
        var cbConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slidingWindowSize(4)
                .build();

        var targets = new String[]{"mini"};
        return factory -> {
            factory.addCircuitBreakerCustomizer(
                    getEventLogger(), targets);
            factory.configure(builder -> {
                builder.circuitBreakerConfig(cbConfig);
            }, targets);
        };
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> autoHalf() {
        var cbConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slidingWindowSize(4)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .build();

        var targets = new String[]{"autoHalf"};
        return factory -> {
            factory.addCircuitBreakerCustomizer(
                    getEventLogger(), targets);
            factory.configure(builder -> {
                builder.circuitBreakerConfig(cbConfig);
            }, targets);
        };
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> halfOpen() {
        var cbConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slidingWindowSize(4)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .permittedNumberOfCallsInHalfOpenState(6)
                .build();

        var targets = new String[]{"halfOpen"};
        return factory -> {
            factory.addCircuitBreakerCustomizer(
                    getEventLogger(), targets);
            factory.configure(builder -> {
                builder.circuitBreakerConfig(cbConfig);
            }, targets);
        };
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> example() {
        var cbConfig = CircuitBreakerConfig.custom()
                .slidingWindowSize(10)
                .failureRateThreshold(75)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(6)
                .ignoreExceptions(ArithmeticException.class)
                .maxWaitDurationInHalfOpenState(Duration.ofSeconds(30))
                .build();

        var tlConfig = TimeLimiterConfig.custom()
                .cancelRunningFuture(true)
                .timeoutDuration(Duration.ofSeconds(3))
                .build();

        return factory -> {
            factory.addCircuitBreakerCustomizer(
                    getEventLogger(), "example");
            factory.configure(builder -> {
                builder.circuitBreakerConfig(cbConfig)
                        .timeLimiterConfig(tlConfig);
            }, "example");
        };
    }
}
