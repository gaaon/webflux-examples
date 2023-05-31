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

@Slf4j
public class ServerSocketChannelSimpleExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open();
        ) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(); // 준비될때까지 blocking
                var selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    var key = selectedKeys.next();
                    selectedKeys.remove();

                    if (key.isAcceptable()) {
                        var clientSocket = ((ServerSocketChannel) key.channel()).accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    }
                    else if (key.isReadable()) {
                        var clientSocket = (SocketChannel) key.channel();
                        var requestBody = getRequestBody(clientSocket);
                        sendResponse(clientSocket, requestBody);
                    }
                }
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
