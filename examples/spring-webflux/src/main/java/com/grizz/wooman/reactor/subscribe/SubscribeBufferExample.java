package com.grizz.wooman.reactor.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class SubscribeBufferExample {
    public static void main(String[] args) {
        log.info("start main");

        var subscriber = new BaseSubscriber<List<Integer>>() {
            private Integer count = 0;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(2);
            }

            @Override
            protected void hookOnNext(List<Integer> value) {
                log.info("value: " + value);
                if (++count == 2) cancel();
            }

            @Override
            protected void hookOnComplete() {
                log.info("complete");
            }
        };

        Flux.fromStream(IntStream.range(0, 10).boxed())
                .buffer(3)
                .subscribe(subscriber);

        log.info("end main");
    }
}
