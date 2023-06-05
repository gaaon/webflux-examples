package com.grizz.wooman.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NettyEchoServerWithoutBootstrap {
    private static MessageToByteEncoder<String> responseDataEncoder() {
        return new MessageToByteEncoder<>() {
            @Override
            protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) {
                log.info("ResponseDataEncoder.encode");
                out.writeCharSequence(msg, StandardCharsets.UTF_8);
            }
        };
    }

    private static ChannelInboundHandler channelInboundHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof ByteBuf) {
                    ByteBuf buf = (ByteBuf) msg;
                    try {
                        ctx.writeAndFlush("This is server.")
                                .addListener(ChannelFutureListener.CLOSE);
                        log.info("Received message: " + buf.toString(CharsetUtil.UTF_8));
                    } finally {
                        ReferenceCountUtil.release(msg);
                    }
                }
            }
        };
    }

    private static ChannelInboundHandler acceptor(EventLoopGroup childGroup) {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                log.info("Acceptor.channelRead");
                var executorGroup = new DefaultEventExecutorGroup(4);

                if (msg instanceof SocketChannel) {
                    SocketChannel socketChannel = (SocketChannel) msg;
                    socketChannel.pipeline().addLast(executorGroup, new LoggingHandler());
                    socketChannel.pipeline().addLast(
                            responseDataEncoder(),
                            channelInboundHandler()
                    );
                    childGroup.register(socketChannel);
                }
            }
        };
    }

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup(4);

        NioServerSocketChannel serverSocketChannel = new NioServerSocketChannel();
        parentGroup.register(serverSocketChannel);
        serverSocketChannel.pipeline().addLast(acceptor(childGroup));

        serverSocketChannel.bind(new InetSocketAddress(8080))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("Server bound to port 8080");
                    }
                });
    }
}