package com.grizz.wooman.javaio.io;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class ServerSocketInputStreamExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start maiin");
        // 서버 소켓 생성
        ServerSocket serverSocket = new ServerSocket(8080);

        // 클라이언트 접속 대기
        Socket clientSocket = serverSocket.accept();

        var inputStream = clientSocket.getInputStream();
        var bis = new BufferedInputStream(inputStream);
        byte[] buffer = new byte[1024];
        int bytesRead = bis.read(buffer);
        String inputLine = new String(buffer, 0, bytesRead);
        log.info("bytes: {}", inputLine);

        clientSocket.close(); // 클라이언트 소켓 닫기
        serverSocket.close(); // 서버 소켓 닫기
        log.info("end main");
    }
}
