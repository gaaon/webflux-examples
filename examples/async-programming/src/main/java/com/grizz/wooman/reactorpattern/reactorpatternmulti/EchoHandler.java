package com.grizz.wooman.reactorpattern.reactorpatternmulti;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public final class EchoHandler implements ChannelHandler {
    static final String POISON_PILL = "BYE";

    final Executor executor;
    final SocketChannel socketChannel;
    final Selector selector;
    final LinkedBlockingQueue<String> msgQ;
    final MsgCodec msgCodec;

    public EchoHandler(final SocketChannel socketChannel, final Selector selector) throws IOException {
        this.socketChannel = socketChannel;
        this.selector = selector;
        this.msgQ = new LinkedBlockingQueue<>();
        this.msgCodec = new MsgCodec();
        this.executor = Executors.newCachedThreadPool(r -> {
            final var thread = new Thread(r);
            thread.setName("Worker");
            return thread;
        });

        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ).attach(this);

        selector.wakeup();
    }

    @Override
    public void read() throws Exception {
        final var buffer = ByteBuffer.allocate(1024);
        socketChannel.read(buffer);
        final var msg = msgCodec.decode(buffer);
        log.info("[" + Thread.currentThread() + "] <=== " + msg);

        this.executor.execute(new Processor(msg));
    }

    @Override
    public void write() throws Exception {
        if (msgQ.isEmpty()) {
            socketChannel.register(selector, SelectionKey.OP_READ).attach(this);
            return;
        }

        final var msg = msgQ.take();
        final var buffer = msgCodec.encode(msg);
        socketChannel.write(buffer);
        log.info("[" + Thread.currentThread() + "] ===> " + msg);

        if (POISON_PILL.equals(msg.trim())) {
            log.info("Closing " + socketChannel);
            socketChannel.close();
        }
    }

    @RequiredArgsConstructor
    class Processor implements Runnable {
        private final String message;

        @Override
        public void run() {
            try {
                log.info("[" + Thread.currentThread() + "] is handling message [" + message + "]");
                Thread.sleep(3000L); // Do some time-consuming computations
                msgQ.put(message);
                socketChannel.register(selector, SelectionKey.OP_WRITE).attach(EchoHandler.this);
                selector.wakeup(); // Wake up the selector because it may be waiting for READ events, but we're not interested in READ now
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MsgCodec {
        ByteBuffer encode(final String msg) {
            return ByteBuffer.wrap(msg.getBytes());
        }

        String decode(final ByteBuffer buffer) {
            return new String(buffer.array(), buffer.arrayOffset(), buffer.remaining());
        }
    }
}
