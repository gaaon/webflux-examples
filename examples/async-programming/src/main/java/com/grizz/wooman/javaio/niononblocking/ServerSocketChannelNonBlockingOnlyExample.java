package com.grizz.wooman.javaio.niononblocking;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ServerSocketChannelNonBlockingOnlyExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var count = 0;

        Long start = null;

        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);

            while (true) {
                var clientSocket = serverChannel.accept();
                if (clientSocket == null) {
                    Thread.sleep(100);
                    continue;
                }
                clientSocket.configureBlocking(false);
                if (start == null) start = System.currentTimeMillis();

                var requestBuffer = ByteBuffer.allocateDirect(1024);
                var requestBody = "";
                while (clientSocket.read(requestBuffer) > 0) {
                    requestBuffer.flip();
                    requestBody = StandardCharsets.UTF_8.decode(requestBuffer).toString();
                    log.info("request: {}", requestBody);
                }

                Thread.sleep(10);

                var response = "received: " + requestBody;
                var responseBuffer = ByteBuffer.wrap(response.getBytes());
                clientSocket.write(responseBuffer);
                clientSocket.close();
                log.info("end client");
                log.info("count: {}", ++count);

                if (count == 1000) break;
            }
        }
        var duration = System.currentTimeMillis() - start;
        log.info("duration: {}", duration);
    }
}
