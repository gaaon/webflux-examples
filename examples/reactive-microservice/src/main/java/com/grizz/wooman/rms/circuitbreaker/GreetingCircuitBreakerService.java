package com.grizz.wooman.rms.circuitbreaker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class GreetingCircuitBreakerService {
    private final ReactiveCircuitBreakerFactory circuitBreakerFactory;
    private final Greeter greeter;
    private final String fallbackMessage = "Hello world!";

    public Mono<String> greeting(String who, Long delayInMillis) {
        return doGreeting(who, delayInMillis)
                .transform(it -> {
                    var circuitBreaker = circuitBreakerFactory.create("normal");
                    return circuitBreaker.run(it, throwable ->
                            Mono.just(fallbackMessage));
                });
    }

    public Mono<String> greetingWithException(String who) {
        Mono<String> mono = Mono.error(new RuntimeException("Error"));

        return mono.transform(it -> {
            var circuitBreaker = circuitBreakerFactory.create("exception");
            return circuitBreaker.run(it, throwable ->
                    Mono.just(fallbackMessage));
        });
    }

    public Mono<String> greetingWithId(String id, String who, Long delayInMillis) {
        return doGreeting(who, delayInMillis)
                .transform(it -> {
                    var circuitBreaker = circuitBreakerFactory.create(id);
                    return circuitBreaker.run(it, throwable ->
                            Mono.just(fallbackMessage));
                });
    }

    public Mono<String> greetingWithIdAndGroup(
            String id,
            String group,
            String who,
            Long delayInMillis) {
        return doGreeting(who, delayInMillis)
                .transform(it -> {
                    var circuitBreaker = circuitBreakerFactory.create(id, group);
                    return circuitBreaker.run(it, throwable ->
                            Mono.just(fallbackMessage));
                });
    }

    private Mono<String> doGreeting(String who,
                                    Long delayInMillis) {
        var duration = Duration.ofMillis(delayInMillis);
        return Mono.delay(duration).then()
                .then(Mono.fromCallable(() ->
                        greeter.generate(who)));
    }
}
