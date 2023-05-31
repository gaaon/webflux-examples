package com.grizz.wooman.etc;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface ExecutorService extends Executor {
    void execute(Runnable command);
    <T> Future<T> submit(Callable<T> task);
    void shutdown();

    public static void main(String[] args) {
    }
}