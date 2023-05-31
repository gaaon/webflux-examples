package com.grizz.wooman.javaio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SocketChannelNonBlockingExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        try (var socketChannel = SocketChannel.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            socketChannel.configureBlocking(false);
            socketChannel.connect(address);

            String request = "This is client.";
            ByteBuffer requestBuffer = ByteBuffer.wrap(request.getBytes());
            socketChannel.write(requestBuffer);

            ByteBuffer res = ByteBuffer.allocate(1024);
            while (socketChannel.read(res) > 0) {
                res.flip();
                log.info("response: {}", StandardCharsets.UTF_8.decode(res));
                res.clear();
            }
        }
        log.info("end main");
    }
}
