package com.grizz.wooman.async;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureMain {
    public static void run() {
        CompletableFuture<Integer> a = CompletableFuture.completedFuture(10);
    }

    public static void main(String[] args) {
        run();
    }
}
