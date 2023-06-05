package com.grizz.wooman.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLoopTest {
    @SneakyThrows
    public static void main(String[] args) {
        EventLoopGroup testGroup = new NioEventLoopGroup(10);

        for (int i = 0; i < 100; i++) {
            testGroup.execute(() -> {
                log.info("{}", Thread.currentThread().getName());
            });
        }

        testGroup.shutdownGracefully();
    }
}