package com.grizz.wooman.javaio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
class EventHandler {
    @RequiredArgsConstructor
    class EventKey {
        private final SelectionKey selectionKey;

        @Getter
        private final EventHandler eventHandler;

        public SocketChannel channel() {
            return (SocketChannel) selectionKey.channel();
        }
    }

    private final Selector selector;
    private Consumer<EventKey> connectConsumer;
    private Consumer<EventKey> readConsumer;

    public EventHandler() throws IOException {
        this.selector = Selector.open();
    }

    private void close() throws IOException {
        this.selector.close();
    }

    public void registerConnect(SocketChannel socketChannel, Consumer<EventKey> consumer)
            throws IOException {
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        connectConsumer = consumer;
    }

    public void registerRead(SocketChannel socketChannel, Consumer<EventKey> consumer)
            throws IOException {
        socketChannel.register(selector, SelectionKey.OP_READ);
        readConsumer = consumer;
    }

    public void listen(Runnable onFinish) throws InterruptedException {
        log.info("start listen");
        var executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                selectLoop:
                while (true) {
                    selector.select();
                    var selectionKeys = selector.selectedKeys();
                    var iterator = selectionKeys.iterator();

                    while (iterator.hasNext()) {
                        var selectionKey = iterator.next();
                        var eventKey = new EventKey(selectionKey, this);
                        iterator.remove();

                        if (selectionKey.isConnectable()) {
                            connectConsumer.accept(eventKey);
                        } else if (selectionKey.isReadable()) {
                            readConsumer.accept(eventKey);
                            break selectLoop;
                        }
                    }
                }
                onFinish.run();
                this.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        });
        executor.shutdown();
        log.info("end listen");
    }
}
