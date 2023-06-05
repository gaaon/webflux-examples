package com.grizz.wooman.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class EventLoopGroupAcceptStudy {
    private static MessageToByteEncoder<String> responseDataEncoder() {
        return new MessageToByteEncoder<String>() {
            @Override
            protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
                log.info("ResponseDataEncoder.encode");
                out.writeCharSequence(msg, StandardCharsets.UTF_8);
            }
        };
    }

    private static ChannelInboundHandler handle() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof ByteBuf) {
                    ByteBuf buf = (ByteBuf) msg;
                    try {
                        ctx.writeAndFlush("This is server.").addListener(ChannelFutureListener.CLOSE);
                        log.info("Received message: " + buf.toString(CharsetUtil.UTF_8));
                    } finally {
                        ReferenceCountUtil.release(msg);
                    }
                }
            }
        };
    }

    private static ChannelInboundHandler accept(EventLoopGroup parentGroup) {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof Channel) {
                    Channel channel = (Channel) msg;
                    log.info("Channel: " + channel);
                    channel.pipeline().addLast(responseDataEncoder(), handle());
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
