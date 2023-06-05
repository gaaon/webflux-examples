package com.grizz.wooman.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SequenceErrorExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.error(new RuntimeException("mono error"))
                .subscribe(value -> {
                    log.info("value: " + value);
                }, error -> {
                    log.error("error: " + error);
                });

        Flux.error(new RuntimeException("flux error"))
                .subscribe(value -> {
                    log.info("value: " + value);
                }, error -> {
                    log.error("error: " + error);
                });
        log.info("end main");
    }
}
