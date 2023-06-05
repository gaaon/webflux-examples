package com.grizz.wooman.netty.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLoopNonIOTaskExample {
    public static void main(String[] args) {
        log.info("start main");
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        for (int i = 0; i < 10; i++) {
            final int idx = i;
            eventLoopGroup.execute(() -> {
                log.info("i: {}", idx);
            });
        }

        eventLoopGroup.shutdownGracefully();
        log.info("end main");
    }
}
