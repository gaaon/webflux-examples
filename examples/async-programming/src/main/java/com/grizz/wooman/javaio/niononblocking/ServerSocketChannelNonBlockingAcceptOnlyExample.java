package com.grizz.wooman.javaio.niononblocking;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

@Slf4j
public class ServerSocketChannelNonBlockingAcceptOnlyExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        log.info("start main");
        try (var serverChannel = ServerSocketChannel.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);

            var clientSocket = serverChannel.accept();
            assert clientSocket == null;
        }
        log.info("end main");
    }
}
