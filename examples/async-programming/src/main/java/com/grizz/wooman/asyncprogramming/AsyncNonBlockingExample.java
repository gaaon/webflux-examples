package com.grizz.wooman.asyncprogramming;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public class AsyncNonBlockingExample {
    public static void main(String[] args) {
        log.info("Start main");
        getResult(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                var nextValue = integer + 1;
                assert nextValue == 1;
            }
        });
        log.info("Finish main");
    }

    public static void getResult(Consumer<Integer> callback) {
        var executor = Executors.newSingleThreadExecutor();
        try {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    log.info("Start getResult");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    var result = 0;
                    try {
                        callback.accept(result);
                    } finally {
                        log.info("Finish getResult");
                    }
                }
            });
        } finally {
            executor.shutdown();
        }
    }
}
