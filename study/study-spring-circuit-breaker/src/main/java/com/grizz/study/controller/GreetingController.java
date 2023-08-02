package com.grizz.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@RequestMapping("/greeting")
@RestController
@RequiredArgsConstructor
public class GreetingController {
    private final ReactiveCircuitBreakerFactory circuitBreakerFactory;
    private AtomicInteger counter = new AtomicInteger(0);

    @GetMapping
    Mono<String> greeting(@RequestParam Long waitTime, @RequestParam String cbId) {
        log.info("[{}] greeting", counter.incrementAndGet());
        return getWeather(waitTime)
                .map(weather -> "Hello world!\nToday's weather is " + weather)
                .transform(it -> circuitBreakerFactory.create(cbId).run(it, throwable ->
                        Mono.just("Hello world!")));
    }

    public Mono<String> getWeather(Long waitTime) {
        return Mono.create((Consumer<MonoSink<String>>) stringMonoSink -> {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    stringMonoSink.success("sunny");
                    log.info("success");
                });
    }

    public void reset() {
        counter.set(0);
    }
}
