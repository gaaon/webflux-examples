package com.grizz.wooman.netty.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NettySeparatedEchoServer2 {
    private static ChannelInboundHandler requestHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof ByteBuf) {
                    try {
                        var buf = (ByteBuf) msg;
                        var len = buf.readableBytes();
                        var charset = StandardCharsets.UTF_8;
                        var body = buf.readCharSequence(len, charset);
                        log.info("RequestHandler.channelRead: " + body);

                        ctx.fireChannelRead(body);
                    } finally {
                        ReferenceCountUtil.release(msg);
                    }
                }
            }
        };
    }

    private static ChannelOutboundHandler responseHandler() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(
                    ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {

                if (msg instanceof String) {
                    log.info("ResponseHandler.write: " + msg);
                    var body = (String) msg;
                    var charset = StandardCharsets.UTF_8;
                    var buf = ctx.alloc().buffer();
                    buf.writeCharSequence(body, charset);
                    ctx.write(buf, promise)
                            .addListener(ChannelFutureListener.CLOSE);
                }
            }
        };
    }

    private static ChannelInboundHandler echoHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                if (msg instanceof String) {
                    var request = (String) msg;
                    log.info("EchoHandler.channelRead: " + request);

                    ctx.writeAndFlush(request);
                }
            }
        };
    }

    private static ChannelInboundHandler acceptor(EventLoopGroup childGroup) {
        var executorGroup = new DefaultEventExecutorGroup(4);

        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                log.info("Acceptor.channelRead");
                if (msg instanceof SocketChannel) {
                    SocketChannel socketChannel = (SocketChannel) msg;
                    socketChannel.pipeline().addLast(
                            executorGroup, new LoggingHandler(LogLevel.INFO));
                    socketChannel.pipeline().addLast(
                            requestHandler(),
                            responseHandler(),
                            echoHandler()
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
                    } else {
                        log.info("Failed to bind to port 8080");
                        parentGroup.shutdownGracefully();
                        childGroup.shutdownGracefully();
                    }
                });
    }
}