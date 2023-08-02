package com.grizz.wooman.test.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GreetingService {
    public void hello(String who) {
        String greeting = prepareGreeting(who);
        log.info(greeting);
    }

    public String greeting(String who) {
        return prepareGreeting(who);
    }

    public Mono<String> greetingMono(String who) {
        return Mono.just(prepareGreeting(who));
    }

    public Integer greetingCount() {
        return 100;
    }

    private String prepareGreeting(String who) {
        return "hello " + who;
    }

    private Integer count = 100;

    private void setCount(Integer c) {
        this.count = c;
    }
}
