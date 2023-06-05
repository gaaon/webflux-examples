package com.grizz.wooman.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Slf4j
public class SingleThreadRunExmample {
    public static void main(String[] args) {
        log.info("start main");
        var executor = Executors.newSingleThreadExecutor();
        try {
            executor.submit(() -> {
                Flux.create(sink -> {
                    for (int i = 1; i <= 5; i++) {
                        log.info("next: {}", i);
                        sink.next(i);
                    }
                }).subscribeOn(
                        Schedulers.single()
                ).subscribe(value -> {
                    log.info("value: " + value);
                });
            });
        } finally {
            executor.shutdown();
        }


    }
}
