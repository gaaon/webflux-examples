package com.grizz.wooman.reactorpattern;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

@Slf4j
public class Proactor implements Runnable {
    private final AsynchronousServerSocketChannel serverSocketChannel;

    @SneakyThrows
    public Proactor(int port) {
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
    }

    @Override
    public void run() {
        var acceptor = new AcceptCompletionHandler(serverSocketChannel);
        serverSocketChannel.accept(null, acceptor);
    }
}
