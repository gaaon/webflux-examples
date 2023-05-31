package com.grizz.wooman.javaio.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;

@Slf4j
public class ServerSocketChannelExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        try (var serverChannel = ServerSocketChannel.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);

            try (var clientSocket = serverChannel.accept()) {
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                clientSocket.read(buffer);
                buffer.flip();

                var request = new String(buffer.array()).trim();
                log.info("request: {}", request);

                var response = "This is server.";
                var responseBuffer = ByteBuffer.wrap(response.getBytes());
                clientSocket.write(responseBuffer);
                responseBuffer.flip();
            }
        }
        log.info("end main");
    }
}
