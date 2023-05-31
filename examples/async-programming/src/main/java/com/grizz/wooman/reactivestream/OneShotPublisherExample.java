package com.grizz.wooman.reactivestream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneShotPublisherExample {
    public static void main(String[] args) throws InterruptedException {
        var publisher = new OneShotPublisher();
        publisher.subscribe(new LogSubscriber<>());

        Thread.sleep(100);
    }
}
