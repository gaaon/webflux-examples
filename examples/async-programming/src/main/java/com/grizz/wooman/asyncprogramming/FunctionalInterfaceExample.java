package com.grizz.wooman.asyncprogramming;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        var consumer = getConsumer();
        consumer.accept(1);

        var consumerAsLambda = getConsumerAsLambda();
        consumerAsLambda.accept(1);

        handleConsumer(consumer);
    }

    public static Consumer<Integer> getConsumer() {
        Consumer<Integer> returnValue = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                log.info("value in interface: {}", integer);
            }
        };
        return returnValue;
    }

    public static Consumer<Integer> getConsumerAsLambda() {
        return integer -> log.info("value in lambda: {}", integer);
    }

    public static void handleConsumer(Consumer<Integer> consumer) {
        log.info("handleConsumer");
        consumer.accept(1);
    }
}
