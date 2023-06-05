package com.grizz.wooman.netty.eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopIOTaskExample {
    public static void main(String[] args) {
        log.info("start main");
        var channel = new NioServerSocketChannel();
        var eventLoopGroup = new NioEventLoopGroup(1);
        eventLoopGroup.register(channel);
        channel.bind(new InetSocketAddress(8080))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("Server bound to port 8080");
                    } else {
                        log.info("Failed to bind to port 8080");
                        eventLoopGroup.shutdownGracefully();
                    }
                });
        log.info("end main");
    }
}
