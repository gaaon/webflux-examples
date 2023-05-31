package com.grizz.wooman.result.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JavaNIOBlockingServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            var requestByteBuffer = ByteBuffer.allocateDirect(1024);

            while (true) {
                SocketChannel clientSocket = serverSocket.accept();

                clientSocket.read(requestByteBuffer);
                requestByteBuffer.flip();
                var request = StandardCharsets.UTF_8.decode(requestByteBuffer);
                log.info("request: {}", request);
                requestByteBuffer.clear();

                var responseByteBuffer = ByteBuffer.wrap("Hello World".getBytes());
                clientSocket.write(responseByteBuffer);
                clientSocket.close();
            }
        }

//        log.info("end main");
    }
}
