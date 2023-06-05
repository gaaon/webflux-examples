package com.grizz.wooman.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TakeExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        Flux.range(1, 10)
                .take(5)
                .doOnNext(value -> {
                    log.info("take: " + value);
                })
                .subscribe();

        Flux.range(1, 10)
                .takeLast(5)
                .doOnNext(value -> {
                    log.info("takeLast: " + value);
                })
                .subscribe();

        Thread.sleep(1000);
        log.info("end main");

    }
}
