package com.grizz.wooman.reactor.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class PublishOnSchedulerExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        Flux.create(sink -> {
            for (var i = 0; i < 5; i++) {
                log.info("next: {}", i);
                sink.next(i);
            }
        }).publishOn(
                Schedulers.single()
        ).doOnNext(item -> {
            log.info("doOnNext: {}", item);
        }).publishOn(
                Schedulers.boundedElastic()
        ).doOnNext(item -> {
            log.info("doOnNext2: {}", item);
        }).subscribe(value -> {
            log.info("value: " + value);
        });
        Thread.sleep(1000);
        log.info("end main");
    }
}
