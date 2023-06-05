package com.grizz.wooman.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SequenceJustExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.just(1)
                .subscribe(value -> {
                    log.info("value: " + value);
                });

        Flux.just(1, 2, 3, 4, 5)
                .subscribe(value -> {
                    log.info("value: " + value);
                });
        log.info("end main");
    }
}
