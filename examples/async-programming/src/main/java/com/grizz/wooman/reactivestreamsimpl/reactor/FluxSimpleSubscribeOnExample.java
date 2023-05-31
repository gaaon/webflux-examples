package com.grizz.wooman.reactivestreamsimpl.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
public class FluxSimpleSubscribeOnExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        getItems()
                .map(i -> {
                    log.info("map {}", i);
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");

        Thread.sleep(1000);
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
