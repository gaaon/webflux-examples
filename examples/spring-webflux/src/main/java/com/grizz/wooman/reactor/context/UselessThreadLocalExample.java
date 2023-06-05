package com.grizz.wooman.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class UselessThreadLocalExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("wooman");

        Flux.create(sink -> {
            log.info("threadLocal: " + threadLocal.get());
            sink.next(1);
        }).publishOn(Schedulers.parallel()
        ).map(value -> {
            log.info("threadLocal: " + threadLocal.get());
            return value;
        }).publishOn(Schedulers.boundedElastic()
        ).map(value -> {
            log.info("threadLocal: " + threadLocal.get());
            return value;
        }).subscribeOn(Schedulers.single()
        ).subscribe();

        Thread.sleep(1000);
        log.info("end main");
    }
}
