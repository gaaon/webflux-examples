package com.grizz.wooman.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MapExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.range(1, 5)
                .map(value -> value * 2)
                .doOnNext(value -> {
                    log.info("doOnNext: " + value);
                })
                .subscribe();

        Flux.range(1, 5)
                .mapNotNull(value -> {
                    if (value % 2 == 0) {
                        return value;
                    }
                    return null;
                })
                .doOnNext(value -> {
                    log.info("doOnNext: " + value);
                })
                .subscribe();
        log.info("end main");
    }
}
