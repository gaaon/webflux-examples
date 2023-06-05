package com.grizz.wooman.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class NettyHttpServerMain {
    private static class CustomHttpServerHandler extends SimpleChannelInboundHandler {
        private HttpRequest request;
        StringBuilder responseData = new StringBuilder();

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                HttpRequest request = this.request = (HttpRequest) msg;

                if (HttpUtil.is100ContinueExpected(request)) {
                    writeResponse(ctx);
                }
                responseData.setLength(0);
                responseData.append(RequestUtils.formatParams(request));
            }
            responseData.append(RequestUtils.evaluateDecoderResult(request));

            if (msg instanceof HttpContent) {
                HttpContent httpContent = (HttpContent) msg;
                responseData.append(RequestUtils.formatBody(httpContent));
                responseData.append(RequestUtils.evaluateDecoderResult(request));

                if (msg instanceof LastHttpContent) {
                    LastHttpContent trailer = (LastHttpContent) msg;
                    responseData.append(RequestUtils.prepareLastResponse(request, trailer));
                    writeResponse(ctx, trailer, responseData);
                }
            }
        }

        private void writeResponse(ChannelHandlerContext ctx) {
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE,
                    Unpooled.EMPTY_BUFFER);
            ctx.write(response);
        }

        private void writeResponse(ChannelHandlerContext ctx, LastHttpContent trailer,
                                   StringBuilder responseData) {
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1,
                    ((HttpObject) trailer).decoderResult().isSuccess() ? OK : BAD_REQUEST,
                    Unpooled.copiedBuffer(responseData.toString(), CharsetUtil.UTF_8));

            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

            if (keepAlive) {
                httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
                        httpResponse.content().readableBytes());
                httpResponse.headers().set(HttpHeaderNames.CONNECTION,
                        HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(httpResponse);

            if (!keepAlive) {
                ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        var bossGroup = new NioEventLoopGroup();
        var workerGroup = new NioEventLoopGroup();

        try {
            var bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new HttpRequestDecoder(),
                                    new HttpResponseEncoder(),
                                    new CustomHttpServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            var f = bootstrap.bind(8080).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
