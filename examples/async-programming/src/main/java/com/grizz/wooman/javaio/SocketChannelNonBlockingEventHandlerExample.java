package com.grizz.wooman.javaio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SocketChannelNonBlockingEventHandlerExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("start main");
        var address = new InetSocketAddress("localhost", 3000);

        var eventHandler = new EventHandler();
        var socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(address);

        eventHandler.registerConnect(socketChannel,
                eventKey -> handleConnect(eventKey));
        eventHandler.listen(() -> {
            try {
                socketChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("end main");
    }

    private static void handleConnect(EventHandler.EventKey eventKey) {
        try {
            var channel = eventKey.channel();
            if (channel.isConnectionPending()) {
                channel.finishConnect();
            }

            String request = "GET / HTTP/1.1\r\nHost: localhost:8080\r\n\r\n";
            ByteBuffer requestBuffer = ByteBuffer.wrap(request.getBytes());
            channel.write(requestBuffer);

            eventKey.getEventHandler().registerRead(channel, key -> handleRead(key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleRead(EventHandler.EventKey eventKey) {
        try {
            var channel = eventKey.channel();
            ByteBuffer res = ByteBuffer.allocate(1024);
            while (channel.read(res) > 0) {
                res.flip();
                log.info("response: {}", StandardCharsets.UTF_8.decode(res));
                res.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
