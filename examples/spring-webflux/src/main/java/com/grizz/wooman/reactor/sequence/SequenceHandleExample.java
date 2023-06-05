package com.grizz.wooman.reactor.sequence;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

@Slf4j
public class SequenceHandleExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        Flux.fromStream(IntStream.range(0, 10).boxed())
                .handle((value, sink) -> {
                    sink.complete();
                    if (value % 2 == 0) {
                        sink.next(value);
                    }
                }).subscribe(value -> {
                    log.info("value: " + value);
                }, error -> {
                    log.error("error: " + error);
                }, () -> {
                    log.info("complete");
                });
        log.info("end main");
    }
}
