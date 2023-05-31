package com.grizz.wooman.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class ListMonoToFluxExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems()
                .flatMapMany(value -> Flux.fromIterable(value))
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    private static Mono<List<Integer>> getItems() {
        return Mono.just(List.of(1, 2, 3, 4, 5));
    }
}
