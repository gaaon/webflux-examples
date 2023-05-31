package com.grizz.wooman.javaio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class ServerSocketChannelSelectorAcceptNoCommentExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                var selectedKeys = selector.selectedKeys();
                for (var key : selectedKeys) {
                    if (key.isAcceptable()) {
                        var clientSocket = ((ServerSocketChannel) key.channel()).accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        log.info("start read");
                        var clientSocket = (SocketChannel) key.channel();
                        var requestBuffer = ByteBuffer.allocate(1024);
                        clientSocket.read(requestBuffer);
                        requestBuffer.flip();
                        var request = new String(requestBuffer.array()).trim();
                        log.info("request: {}", request);

                        var response = "This is server.";
                        var responseBuffer = ByteBuffer.wrap(response.getBytes());
                        clientSocket.write(responseBuffer);
                        clientSocket.close();
                        log.info("end read");
                    }
                }
                selectedKeys.clear();
            }
        }
//        log.info("end main");
    }
}
