package com.grizz.wooman.reactorpattern;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
public class HttpCompletionHandler
        implements CompletionHandler<Integer, Void> {
    private final MsgCodec msgCodec;
    private final ByteBuffer requestByteBuffer;
    private final AsynchronousSocketChannel socketChannel;

    public HttpCompletionHandler(AsynchronousSocketChannel socketChannel) {
        this.msgCodec = new MsgCodec();
        this.requestByteBuffer = ByteBuffer.allocateDirect(1024);
        this.socketChannel = socketChannel;
        this.socketChannel.read(this.requestByteBuffer, null, this);
    }

    @Override
    public void completed(Integer result, Void attachment) {
        String requestBody = handleRequest();
        log.info("requestBody: {}", requestBody);
        sendResponse(requestBody);
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        log.error("Failed to read from client", exc);
    }

    @SneakyThrows
    private String handleRequest() {
        return msgCodec.decode(requestByteBuffer);
    }

    @SneakyThrows
    private void sendResponse(String requestBody) {
        ByteBuffer responeByteBuffer = msgCodec.encode(requestBody);
        socketChannel.write(responeByteBuffer, null, new CompletionHandler<>() {
            @SneakyThrows
            @Override
            public void completed(
                    Integer result, Object attachment) {
                socketChannel.close();
            }

            @SneakyThrows
            @Override
            public void failed(
                    Throwable exc, Object attachment) {
                socketChannel.close();
            }
        });
    }
}
