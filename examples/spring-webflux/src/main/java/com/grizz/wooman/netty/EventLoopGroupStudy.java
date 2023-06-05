package com.grizz.wooman.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopGroupStudy {

    public static void main(String[] args) {
        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup(4);
        final NioServerSocketChannel channel = new NioServerSocketChannel();
        parent.register(channel);

        final ChannelFuture bind = channel.bind(new InetSocketAddress(8080));
        bind.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("{}", future.channel());
                future.channel().pipeline()
                        .addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                final Channel channel = (Channel) msg;
                                log.info("channel : {}", channel);
                                child.register(channel);

                                channel.pipeline()
                                        .addLast(new ChannelInboundHandlerAdapter(){
                                            @Override
                                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                log.info("msg: {}", msg);
                                                var m = (ByteBuf) msg;
                                                m.release();

                                                ctx.writeAndFlush("hello");
                                            }
                                        });
                            }
                        });
            }
        });
    }
}