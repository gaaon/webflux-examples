package com.grizz.wooman.javaio.aio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AsyncFileChannelReadCallbackExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var file = new File(AsyncFileChannelReadCallbackExample.class
                .getClassLoader()
                .getResource("hello.txt")
                .getFile());

        var channel = AsynchronousFileChannel.open(file.toPath());
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        channel.read(buffer, 0, null, new CompletionHandler<>() {
            @SneakyThrows
            @Override
            public void completed(Integer result, Object attachment) {
                buffer.flip();
                var resultString = StandardCharsets.UTF_8.decode(buffer);
                log.info("result: {}", resultString);
                channel.close();
            }

            @Override
            public void failed(Throwable ex, Object attachment) { /* do nothing */ }
        });

        while (channel.isOpen()) {
            log.info("Reading...");
        }
        log.info("end main");
    }
}
