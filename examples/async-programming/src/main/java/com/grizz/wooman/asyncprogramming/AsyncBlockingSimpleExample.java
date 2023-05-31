package com.grizz.wooman.asyncprogramming;

import java.util.function.Consumer;

public class AsyncBlockingSimpleExample {
    public static void main(String[] args) {
        getResult(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                var nextValue = integer + 1;
                assert nextValue == 1;
            }
        });
    }

    public static void getResult(
            Consumer<Integer> callback
    ) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var result = 0;
        callback.accept(result);
    }
}
