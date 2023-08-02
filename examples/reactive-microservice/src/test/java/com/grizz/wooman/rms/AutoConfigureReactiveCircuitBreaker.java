package com.grizz.wooman.rms;

import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration;
import io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JAutoConfiguration;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(
        classes = {
                ReactiveResilience4JAutoConfiguration.class,
                Resilience4JAutoConfiguration.class,
                CircuitBreakerAutoConfiguration.class,
                TimeLimiterAutoConfiguration.class
        }
)
public @interface AutoConfigureReactiveCircuitBreaker {
}
