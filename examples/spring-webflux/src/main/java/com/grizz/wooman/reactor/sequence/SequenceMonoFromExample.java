package com.grizz.wooman.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class SequenceMonoFromExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.fromCallable(() -> {
            return 1;
        }).subscribe(value -> {
            log.info("value fromCallable: " + value);
        });
        Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            return 1;
        })).subscribe(value -> {
            log.info("value fromFuture: " + value);
        });
        Mono.fromSupplier(() -> {
            return 1;
        }).subscribe(value -> {
            log.info("value fromSupplier: " + value);
        });
        Mono.fromRunnable(() -> {
            /* do nothing */
        }).subscribe(null, null, () -> {
            log.info("complete fromRunnable");
        });

        log.info("end main");
    }
}
