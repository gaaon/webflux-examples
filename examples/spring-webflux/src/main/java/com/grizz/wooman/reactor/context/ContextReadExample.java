package com.grizz.wooman.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ContextReadExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        Flux.just(1)
                .flatMap(value -> {
                    return Mono.deferContextual(contextView -> {
                        String name = contextView.get("name");
                        log.info("name: " + name);
                        return Mono.just(value);
                    });
                }).contextWrite(context ->
                        context.put("name", "taewoo")
                ).subscribe();

        Thread.sleep(1000);
        log.info("end main");
    }
}
