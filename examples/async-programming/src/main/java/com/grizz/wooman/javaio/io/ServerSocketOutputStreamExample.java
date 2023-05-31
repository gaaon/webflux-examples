package com.grizz.wooman.javaio.io;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class ServerSocketOutputStreamExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start maiin");
        // 서버 소켓 생성
        ServerSocket serverSocket = new ServerSocket(8080);

        // 클라이언트 접속 대기
        Socket clientSocket = serverSocket.accept();

        byte[] buffer = new byte[1024];
        clientSocket.getInputStream().read(buffer);

        var outputStream = clientSocket.getOutputStream();
        var bos = new BufferedOutputStream(outputStream);
        var bytes = "Hello World".getBytes();
        bos.write(bytes);
        bos.flush();

        // 클라이언트 소켓 닫기
        clientSocket.close();
        // 서버 소켓 닫기
        serverSocket.close();
        log.info("end main");
    }
}
