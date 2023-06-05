package com.grizz.wooman.reactor.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FlatMapExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        Flux.range(1, 5)
                .flatMap(value -> {
                    return Flux.range(1, 2)
                            .map(value2 -> value + ", " + value2)
                            .publishOn(Schedulers.parallel());
                })
                .doOnNext(value -> {
                    log.info("doOnNext: " + value);
                })
                .subscribe();
        Thread.sleep(1000);
        log.info("end main");

    }
}
