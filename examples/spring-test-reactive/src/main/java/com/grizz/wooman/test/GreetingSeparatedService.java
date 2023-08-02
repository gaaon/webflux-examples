package com.grizz.wooman.test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class GreetingSeparatedService {
    public void hello(String who) {
        String greeting = new GreetingGenerator(who)
                .execute();
        log.info(greeting);
    }

    public String greeting(String who) {
        return new GreetingGenerator(who).execute();
    }

    public Mono<String> greetingMono(String who) {
        return Mono.just(new GreetingGenerator(who).execute());
    }

    public Integer greetingCount() {
        return 100;
    }
}
