package com.grizz.wooman.javaio.aio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AsyncServerSocketCallbackExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var serverSocketChannel = AsynchronousServerSocketChannel.open();
        var address = new InetSocketAddress("localhost", 8080);
        serverSocketChannel.bind(address);

        serverSocketChannel.accept(null, new CompletionHandler<>() {
            @Override
            public void completed(AsynchronousSocketChannel clientSocket, Object attachment) {
                log.info("accepted");
                var requestBuffer = ByteBuffer.allocateDirect(1024);

                clientSocket.read(requestBuffer, null, new CompletionHandler<>() {
                    @SneakyThrows
                    @Override
                    public void completed(Integer a, Object attachment) {
                        requestBuffer.flip();
                        var request = StandardCharsets.UTF_8.decode(requestBuffer);
                        log.info("request: {}", request);

                        var response = "This is server.";
                        var responseBuffer = ByteBuffer.wrap(response.getBytes());
                        clientSocket.write(responseBuffer);
                        clientSocket.close();
                        log.info("end client");
                    }

                    @Override
                    public void failed(Throwable ex, Object attachment) { /* do nothing */ }
                });
            }

            @Override
            public void failed(Throwable ex, Object attachment) { /* do nothing */ }
        });

        Thread.sleep(100_000);
        log.info("end main");
}
}
