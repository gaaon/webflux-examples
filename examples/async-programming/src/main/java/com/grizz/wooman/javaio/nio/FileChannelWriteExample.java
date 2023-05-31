package com.grizz.wooman.javaio.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

@Slf4j
public class FileChannelWriteExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var file = new File(FileChannelWriteExample.class
                .getClassLoader()
                .getResource("hello.txt")
                .getFile());

        var mode = StandardOpenOption.WRITE;
        try (var fileChannel = FileChannel.open(file.toPath(), mode)) {
            var byteBuffer = ByteBuffer.wrap("hello world2".getBytes());
            var result = fileChannel.write(byteBuffer);
            log.info("result: {}", result);
        }
        log.info("end main");
    }
}
