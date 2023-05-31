package com.grizz.wooman.etc;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Set;

public abstract class Selector implements Closeable {
    public abstract int select() throws IOException;
    public abstract Set<SelectionKey> selectedKeys();
}

//public class CompletableFuture<T> implements Future<T>, CompletionStage<T> {
//    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) { … }
//    public static CompletableFuture<Void> runAsync(Runnable runnable) { … }
//
//    public boolean complete(T value) { … }
//    public boolean isCompletedExceptionally() { … }
//
//    public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs) { …  }
//    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs) { …  }
//}