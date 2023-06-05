package com.grizz.wooman.reactor.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class SequenceGenerateExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next(state);
                    if (state == 9) {
                        sink.complete();
                    }
                    return state + 1;
                }
        ).subscribe(value -> {
            log.info("value: " + value);
        }, error -> {
            log.error("error: " + error);
        }, () -> {
            log.info("complete");
        });
    }
}
