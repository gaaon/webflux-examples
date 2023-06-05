package com.grizz.wooman.netty;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopNoChannelExample {
    private static ChannelInboundHandler accept(EventLoopGroup parentGroup) {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof Channel) {
                    Channel channel = (Channel) msg;
                    log.info("Channel: " + channel);
                    parentGroup.register(channel);
                }
            }
        };
    }

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        final NioServerSocketChannel channel = new NioServerSocketChannel();
        parentGroup.register(channel);
        channel.pipeline().addLast(accept(parentGroup));

        channel.bind(new InetSocketAddress(8080))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("Server bound to port 8080");
                    } else {
                        log.info("Failed to bind to port 8080");
                        parentGroup.shutdownGracefully();
                    }
                });
    }
}
