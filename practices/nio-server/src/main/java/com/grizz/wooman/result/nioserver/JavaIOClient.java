package com.grizz.wooman.result.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class JavaIOClient {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 8080));

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("This is client.".getBytes());
            outputStream.flush();

            byte[] bytes = new byte[1024];
            socket.getInputStream().read(bytes);
            log.info("response: {}", new String(bytes).trim());
        }
        log.info("end main");
    }
}
