package com.grizz.wooman.completablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureCompleteExample {
    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        Future futureToComplete = FutureHelper.getFuture();
//        futureToComplete.complete(null);
    }
}
