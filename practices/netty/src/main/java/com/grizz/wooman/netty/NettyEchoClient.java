package com.grizz.wooman.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;

public class NettyEchoClient {
    @SneakyThrows
    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();

            var client = bootstrap
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(),
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new NettyEchoClientHandler()
                            );
                        }
                    });

            client.connect("localhost", 8080).sync()
                    .channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
