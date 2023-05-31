package com.grizz.wooman.javaio.aio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@Slf4j
public class AsyncFileChannelReadFutureExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var file = new File(AsyncFileChannelReadFutureExample.class
                .getClassLoader()
                .getResource("hello.txt")
                .getFile());

        try(var channel = AsynchronousFileChannel.open(file.toPath())) {
            var buffer = ByteBuffer.allocateDirect(1024);
            Future<Integer> channelRead = channel.read(buffer, 0);
            while (!channelRead.isDone()) {
                log.info("Reading...");
            }
            buffer.flip();
            var result = StandardCharsets.UTF_8.decode(buffer);
            log.info("result: {}", result);
        }
        log.info("end main");
    }
}
