package com.grizz.wooman.reactor.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ImmediateSchedulerExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        Flux.create(sink -> {
            for (int j = 1; j <= 5; j++) {
                log.info("next: {}", j);
                sink.next(j);
            }
        }).subscribeOn(
                Schedulers.immediate()
        ).subscribe(value -> {
            log.info("value: " + value);
        });
        log.info("end main");
        Thread.sleep(1000);
    }
}
