package com.grizz.wooman.completablefuture;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureHelper {
    public static Future<Integer> getFuture() {
        var executor = Executors.newSingleThreadExecutor();

        try {
            return executor.submit(() -> {
                return 1;
            });
        } finally {
            executor.shutdown();
        }
    }

    public static Future<Integer> getFutureCompleteAfter1s() {
        var executor = Executors.newSingleThreadExecutor();

        try {
            return executor.submit(() -> {
                Thread.sleep(1000);
                return 1;
            });
        } finally {
            executor.shutdown();
        }
    }

    public static Future<Integer> getFutureWithException() {
        var executor = Executors.newSingleThreadExecutor();

        try {
            return executor.submit(() -> {
                throw new RuntimeException("Error");
            });
        } finally {
            executor.shutdown();
        }
    }
}
