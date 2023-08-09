package com.grizz.wooman.reactorpattern;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
@RequiredArgsConstructor
public class AcceptCompletionHandler
        implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private final AsynchronousServerSocketChannel serverSocketChannel;

    @Override
    public void completed(
            AsynchronousSocketChannel socketChannel, Void attachment) {
        serverSocketChannel.accept(null, this);

        new HttpCompletionHandler(socketChannel);
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        log.error("Failed to accept connection", exc);
    }
}
