package com.grizz.wooman.reactivestream.example;

import lombok.SneakyThrows;

import java.util.concurrent.Flow;

public class FixedIntPublisherExample {
    @SneakyThrows
    public static void main(String[] args) {
        Flow.Publisher publisher = new FixedIntPublisher();
        Flow.Subscriber subscriber = new RequestNSubscriber<>(Integer.MAX_VALUE);
        publisher.subscribe(subscriber);

        Thread.sleep(100);
    }
}