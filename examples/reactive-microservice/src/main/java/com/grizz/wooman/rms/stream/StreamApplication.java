package com.grizz.wooman.rms.stream;

import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerStreamEventsAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayMetricsAutoConfiguration;

@SpringBootApplication(
        exclude = {
                CircuitBreakerAutoConfiguration.class,
                CircuitBreakerStreamEventsAutoConfiguration.class,
                Resilience4JAutoConfiguration.class,
                ReactiveResilience4JAutoConfiguration.class,
                GatewayAutoConfiguration.class,
                GatewayMetricsAutoConfiguration.class,
        }
)
public class StreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args);
    }
}
