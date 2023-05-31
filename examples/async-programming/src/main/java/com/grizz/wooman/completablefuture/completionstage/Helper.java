package com.grizz.wooman.completablefuture.completionstage;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
public class Helper {
    @SneakyThrows
    public static CompletionStage<Integer> finishedStage() {
        var future = CompletableFuture.supplyAsync(() -> {
            log.info("return in future");
            return 1;
        });
        Thread.sleep(100);
        return future;
    }

    public static CompletionStage<Integer> completionStage() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("return in future");
            return 1;
        });
    }

    public static CompletionStage<Integer> runningStage() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                log.info("I'm running!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });
    }

    public static CompletionStage<Integer> completionStageAfter1s() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getCompletionStage");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });
    }

    public static CompletionStage<Integer> addOne(int value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return value + 1;
        });
    }

    public static CompletionStage<String> addResultPrefix(int value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "result: " + value;
        });
    }

    public static CompletableFuture<Integer> waitAndReturn(int millis, int value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("waitAndReturn: {}ms", millis);
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return value;
        });
    }
}

//public interface Future<V> {
//    boolean cancel(boolean mayInterruptIfRunning);
//    boolean isCancelled();
//    boolean isDone();
//    V get() throws InterruptedException, ExecutionException;
//    V get(long timeout, TimeUnit unit)
//            throws InterruptedException, ExecutionException, TimeoutException;
//}