package com.grizz.wooman.javaio.niononblocking;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
public class ServerSocketChannelNonBlockingMultiExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var count = 0;

        Long start = null;

        var executor = Executors.newFixedThreadPool(30);
        List<CompletableFuture> futures = new ArrayList<>();

        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);

            while (true) {
                var clientSocket = serverChannel.accept();
                if (clientSocket == null) { // clientSocket이 없으면 wait
                    Thread.sleep(100);
                    continue;
                }
                if (start == null) start = System.currentTimeMillis();

                // clientSocket을 처리하기 위해 별도 쓰레드에 할당
                var future = CompletableFuture.runAsync(() -> {
                    // clientSocket을 처리하는 로직
                    try {
                        clientSocket.configureBlocking(false);

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
                    } catch (IOException|InterruptedException e) {
                        e.printStackTrace();
                    }
                }, executor);
                futures.add(future);

                log.info("count: {}", ++count);
                if (count == 1000) break;
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        var duration = System.currentTimeMillis() - start;
        log.info("duration: {}", duration);
        executor.shutdown();
    }
}
