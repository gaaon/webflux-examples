package com.grizz.wooman.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SequenceEmptyExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.empty()
                .subscribe(value -> {
                    log.info("value: " + value);
                }, null, () -> {
                    log.info("complete");
                });
        Flux.empty()
                .subscribe(value -> {
                    log.info("value: " + value);
                }, null, () -> {
                    log.info("complete");
                });
        log.info("end main");
    }
}
