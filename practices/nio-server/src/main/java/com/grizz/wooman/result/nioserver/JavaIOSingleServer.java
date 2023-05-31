package com.grizz.wooman.result.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class JavaIOSingleServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            Socket clientSocket = serverSocket.accept();
            byte[] buffer = new byte[1024];
            clientSocket.getInputStream().read(buffer);
            log.info("request: {}", new String(buffer).trim());

            var outputStream = clientSocket.getOutputStream();
            var bos = new BufferedOutputStream(outputStream);
            var bytes = "Hello World".getBytes();
            bos.write(bytes);
            bos.flush();

            clientSocket.close();
        }
        log.info("end main");
    }
}
