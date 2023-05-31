package com.grizz.wooman.reactorpattern.reactorpatterbyme;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Reactor implements Runnable {
    final Selector selector;
    final Executor eventLoop;
    final ServerSocketChannel serverSocket;
    final ChannelHandler serverAcceptor;

    public Reactor(final ChannelHandler serverAcceptor) throws IOException {
        this.serverAcceptor = serverAcceptor;
        selector = Selector.open();
        eventLoop = Executors.newSingleThreadExecutor(runnable -> {
            final Thread thread = new Thread(runnable);
            thread.setName("NioEventLoop");
            return thread;
        });
        serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT).attach(serverAcceptor);
    }

    public void bind(final int port) throws IOException {
        serverSocket.bind(new InetSocketAddress(port));
        eventLoop.execute(this);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (selector.select() == 0) {
                    continue;
                }

                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(this::dispatch);
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(final SelectionKey selectionKey) {
        final ChannelHandler handler = (ChannelHandler) selectionKey.attachment();
        try {
            if (selectionKey.isReadable() || selectionKey.isAcceptable()) {
                handler.read();
            } else if (selectionKey.isWritable()) {
                handler.write();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiredArgsConstructor
    static class ServerAcceptor implements ChannelHandler {
        final ServerSocketChannel serverSocketChannel;
        final Selector selector;

        @Override
        public void read() throws Exception {
            new EchoHandler(serverSocketChannel.accept(), selector);
        }

        @Override
        public void write() {
            throw new UnsupportedOperationException();
        }
    }
}
