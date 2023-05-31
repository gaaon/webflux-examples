package com.grizz.wooman.javaio.selector;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ServerSocketChannelSelectorCommentExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        log.info("start main");

        var count = 0;
        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open();
        ) {
            // serverSocketChannel을 생성하고, localhost:8080으로 바인딩
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);

            // serverChannel을 non-blocking으로 설정
            serverChannel.configureBlocking(false);

            // serverChannel의 Accept 작업을 selector에 등록
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            // 무한 루프를 통해서 지속적으로 채널의 작업들을 처리
            while (true) {
                // 준비될때까지 쓰레드 blocking
                selector.select();

                // selector에서 준비가 완료된 작업 목록을 가져옴
                var selectedKeys = selector.selectedKeys().iterator();

                // 준비가 완료된 작업들을 하나씩 처리
                while (selectedKeys.hasNext()) {
                    var key = selectedKeys.next();

                    // 준비가 완료된 작업 목록에서 제외한다
                    selectedKeys.remove();

                    if (!key.isValid()) return;

                    if (key.isAcceptable()) { // 작업이 ACCEPT 라면
                        // accept를 통해서 clientSocket에 접근
                        var clientSocket = ((ServerSocketChannel) key.channel()).accept();

                        // clientSocket을 non-blocking으로 설정
                        clientSocket.configureBlocking(false);

                        // clientSocket의 read 작업을 selector에 등록
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    }
                    else if (key.isReadable()) { // 작업이 READ 라면
                        // clientSocket에 접근
                        var clientSocket = (SocketChannel) key.channel();
                        // clientSocket으로부터 데이터를 읽음
                        var requestBuffer = ByteBuffer.allocateDirect(1024);
                        clientSocket.read(requestBuffer);
                        requestBuffer.flip();

                        var requestBody = StandardCharsets.UTF_8.decode(requestBuffer);
                        var response = "received: " + requestBody;
                        // clientSocket에 데이터를 씀
                        var responseBuffer = ByteBuffer.wrap(response.getBytes());
                        clientSocket.write(responseBuffer);
                        responseBuffer.clear();

                        // clientSocket을 닫음
                        clientSocket.close();
                    }
                }
                if (count == 1000) break;
            }
        }
    }

    private static String getRequestBody(SocketChannel clientSocket) throws IOException {
        var requestBuffer = ByteBuffer.allocate(1024);
        clientSocket.read(requestBuffer);
        requestBuffer.flip();
        return new String(requestBuffer.array()).trim();
    }

    @SneakyThrows
    private static void sendResponse(SocketChannel clientSocket, String requestBody) throws IOException {
        log.info("request: {}", requestBody);
        Thread.sleep(10);
        var response = "received: " + requestBody;
        var responseBuffer = ByteBuffer.wrap(response.getBytes());
        clientSocket.write(responseBuffer);
        responseBuffer.clear();
        clientSocket.close();
    }
}
