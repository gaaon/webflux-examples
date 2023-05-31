package com.grizz.wooman.javaio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class ServerSocketChannelSelectorAcceptExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        log.info("start main");

        try (var serverChannel = ServerSocketChannel.open()) {
            // selector 생성
            var selector = Selector.open();

            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            // serverChannel을 non-blocking으로 설정
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(); // 준비될때까지 blocking
                var selectedKeys = selector.selectedKeys();
                for (var key : selectedKeys) {
                    if (!key.isValid()) return;

                    if (key.isAcceptable()) { // event가 ACCEPT 라면
                        // accept를 통해서 clientSocket에 접근
                        var clientSocket = ((ServerSocketChannel) key.channel()).accept();

                        // clientSocket을 non-blocking으로 설정
                        clientSocket.configureBlocking(false);

                        // clientSocket을 selector에 등록
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) { // event가 READ 라면
                        // clientSocket에 접근
                        var clientSocket = (SocketChannel) key.channel();
                        // clientSocket으로부터 데이터를 읽어옴
                        var requestBuffer = ByteBuffer.allocate(1024);
                        clientSocket.read(requestBuffer);
                        requestBuffer.flip();

                        // 읽어온 데이터를 String으로 변환 후 출력
                        var request = new String(requestBuffer.array()).trim();
                        log.info("request: {}", request);

                        // clientSocket에 데이터를 씀
                        var response = "This is server.";
                        var responseBuffer = ByteBuffer.wrap(response.getBytes());
                        clientSocket.write(responseBuffer);
                        responseBuffer.clear();
                        clientSocket.close();

                    }
                }
                selectedKeys.clear();
            }
        }
//        log.info("end main");
    }
}
