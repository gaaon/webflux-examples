package com.grizz.wooman.javaio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SocketChannelNonBlockingSelectorExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var address = new InetSocketAddress("localhost", 3000);

        try (var selector = Selector.open();
             var socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);
            var connected = socketChannel.connect(address);
            log.info("connected: {}", connected);
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            selectLoop:
            while (true) {
                var selectedCount = selector.select();
                log.info("selected: {}", selectedCount);

                var selectionKeys = selector.selectedKeys();
                var iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    var selectionKey = iterator.next();
                    iterator.remove();

                    if (selectionKey.isConnectable()) {
                        onConnect(selectionKey);
                    } else if (selectionKey.isReadable()) {
                        onRead(selectionKey);
                        break selectLoop;
                    }
                }
            }
        }
        log.info("end main");
    }

    private static void onConnect(SelectionKey selectionKey)
            throws IOException {

        var channel = (SocketChannel) selectionKey.channel();
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }

        String request = "GET / HTTP/1.1\r\nHost: localhost:8080\r\n\r\n";
        ByteBuffer requestBuffer = ByteBuffer.wrap(request.getBytes());
        channel.write(requestBuffer);

        var selector = selectionKey.selector();
        channel.register(selector, SelectionKey.OP_READ);
    }

    private static void onRead(SelectionKey selectionKey)
            throws IOException {
        var channel = (SocketChannel) selectionKey.channel();
        ByteBuffer res = ByteBuffer.allocate(1024);
        while (channel.read(res) > 0) {
            res.flip();
            log.info("response: {}", StandardCharsets.UTF_8.decode(res));
            res.clear();
        }
    }
}
