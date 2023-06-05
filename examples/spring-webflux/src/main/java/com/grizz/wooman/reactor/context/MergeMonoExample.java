package com.grizz.wooman.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MergeMonoExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono<String> nameMono = Mono.just("taewoo");

        Mono.just("Hello")
                .flatMap(s -> nameMono.map(name -> {
                    return s + " " + name;
                }))
                .subscribe(r -> {
                    log.info("result: {}", r);
                });

        Mono.just("Hello")
                .flatMap(s -> nameMono.map(name -> {
                    log.info("name: {}", name);
                    return s;
                })).subscribe(r -> {
                    log.info("result: {}", r);
                });
        log.info("end main");
    }
}
