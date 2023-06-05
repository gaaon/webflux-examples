package com.grizz.wooman.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ErrorConsumerExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.error(new RuntimeException("error"))
                .subscribe(value -> {
                    log.info("value: " + value);
                }, error -> {
                    log.info("error: " + error);
                });
        log.info("end main");
    }
}
