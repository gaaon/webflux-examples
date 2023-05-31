package com.grizz.wooman.completablefuture;

import java.util.concurrent.Future;

public class FutureCancelExample {
    public static void main(String[] args) {
        Future future = FutureHelper.getFuture();
        var successToCancel = future.cancel(true);
        assert future.isCancelled();
        assert future.isDone();
        assert successToCancel;

        successToCancel = future.cancel(true);
        assert future.isCancelled();
        assert future.isDone();
        assert !successToCancel;
    }
}
