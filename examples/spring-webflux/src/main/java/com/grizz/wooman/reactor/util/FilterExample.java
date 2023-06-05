package com.grizz.wooman.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FilterExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.range(1, 5)
                .filter(value -> value % 2 == 0)
                .doOnNext(value -> {
                    log.info("doOnNext: " + value);
                })
                .subscribe();
        log.info("end main");
    }
}
