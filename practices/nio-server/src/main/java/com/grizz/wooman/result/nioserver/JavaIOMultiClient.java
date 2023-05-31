package com.grizz.wooman.result.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Array;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class JavaIOMultiClient {
    private static final ExecutorService executor = Executors.newFixedThreadPool(50);

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        long start = System.currentTimeMillis();
        var futures = new ArrayList<CompletableFuture>();

        for (var i = 0; i < 1000; i++) {
            var future = CompletableFuture.runAsync(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress("localhost", 8080));

                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("This is client.".getBytes());
                    outputStream.flush();

                    byte[] bytes = new byte[1024];
                    socket.getInputStream().read(bytes);
                    log.info("response: {}", new String(bytes).trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executor);

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        log.info("end main");

        long end = System.currentTimeMillis();
        log.info("time: {}s", (end - start)/1000.0);
        executor.shutdown();
    }
}
