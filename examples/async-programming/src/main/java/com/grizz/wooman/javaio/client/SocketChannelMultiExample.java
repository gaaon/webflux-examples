package com.grizz.wooman.javaio.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
public class SocketChannelMultiExample {
    public static void main(String[] args) throws IOException {
        List<CompletableFuture> completableFutures = new ArrayList<>();
        log.info("start main");

        var executor = Executors.newFixedThreadPool(30);

        for (var i = 0; i < 1000; i++) {
            var future = CompletableFuture.runAsync(() -> {
                log.info("start send");
                try {
                    try (var socketChannel = SocketChannel.open()) {
                        var address = new InetSocketAddress("localhost", 8080);
                        var connected = socketChannel.connect(address);
                        log.info("connected: {}", connected);

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("end send");
            }, executor);

            completableFutures.add(future);
        }

        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        log.info("end main");
    }
}
