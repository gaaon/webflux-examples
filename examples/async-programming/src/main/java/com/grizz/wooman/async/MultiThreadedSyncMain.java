package com.grizz.wooman.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class MultiThreadedSyncMain {
    public static void run() {
        int a = 10;
        int b = 20;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(() -> a + b);

        Integer result = -1;
        try {
            result = future.get();
        } catch (Exception e) {
            log.error("Error while getting result from future", e);
        }

        System.out.println("Result: " + result);
    }

    public static void main(String[] args) {
        run();
    }
}
