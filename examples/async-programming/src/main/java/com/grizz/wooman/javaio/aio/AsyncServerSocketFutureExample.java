package com.grizz.wooman.javaio.aio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@Slf4j
public class AsyncServerSocketFutureExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var serverSocketChannel = AsynchronousServerSocketChannel.open();
        var address = new InetSocketAddress("localhost", 8080);
        serverSocketChannel.bind(address);

        Future<AsynchronousSocketChannel> clientSocketFuture = serverSocketChannel.accept();
        while (!clientSocketFuture.isDone()) {
            Thread.sleep(100);
            log.info("Waiting...");
        }
        var clientSocket = clientSocketFuture.get();

        var requestBuffer = ByteBuffer.allocateDirect(1024);
        Future<Integer> channelRead = clientSocket.read(requestBuffer);
        while (!channelRead.isDone()) {
            log.info("Reading...");
        }

        requestBuffer.flip();
        var request = StandardCharsets.UTF_8.decode(requestBuffer);
        log.info("request: {}", request);

        var response = "This is server.";
        var responseBuffer = ByteBuffer.wrap(response.getBytes());
        clientSocket.write(responseBuffer);
        clientSocket.close();
        log.info("end client");
}
}
