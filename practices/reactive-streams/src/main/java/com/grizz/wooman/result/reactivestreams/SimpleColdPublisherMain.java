package com.grizz.wooman.result.reactivestreams;

import lombok.SneakyThrows;

public class SimpleColdPublisherMain {
    @SneakyThrows
    public static void main(String[] args) {
        var publisher = new SimpleColdPublisher();
        var subscriber = new SimpleSubscriber<Integer>();
        publisher.subscribe(subscriber);

        Thread.sleep(1000);

        var subscriber2 = new SimpleSubscriber<Integer>();
        publisher.subscribe(subscriber2);
    }
}
