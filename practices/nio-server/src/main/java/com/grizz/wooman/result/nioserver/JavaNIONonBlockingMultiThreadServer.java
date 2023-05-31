package com.grizz.wooman.result.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class JavaNIONonBlockingMultiThreadServer {
    private static final ExecutorService executor = Executors.newFixedThreadPool(50);

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);

            while (true) {
                SocketChannel clientSocket = serverSocket.accept();
                if (clientSocket == null) {
                    Thread.sleep(100);
                    continue;
                }

                CompletableFuture.runAsync(() -> {
                    var requestByteBuffer = ByteBuffer.allocateDirect(1024);
                    try {
                        while (clientSocket.read(requestByteBuffer) == 0) {
                            Thread.sleep(100);
                        }

                        requestByteBuffer.flip();
                        var request = StandardCharsets.UTF_8.decode(requestByteBuffer);
                        log.info("request: {}", request);
                        requestByteBuffer.clear();

                        Thread.sleep(10);

                        var responseByteBuffer = ByteBuffer.wrap("Hello World".getBytes());
                        clientSocket.write(responseByteBuffer);
                        clientSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, executor);
            }
        }

//        log.info("end main");
    }
}
