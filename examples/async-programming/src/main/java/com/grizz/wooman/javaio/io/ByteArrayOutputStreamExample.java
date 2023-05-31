package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class ByteArrayOutputStreamExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");

        try (var baos = new ByteArrayOutputStream()) {
            baos.write(100);
            baos.write(101);
            baos.write(102);
            baos.write(103);
            baos.write(104);

            var bytes = baos.toByteArray();
            log.info("bytes: {}", bytes);
        }

        log.info("end main");
    }
}
