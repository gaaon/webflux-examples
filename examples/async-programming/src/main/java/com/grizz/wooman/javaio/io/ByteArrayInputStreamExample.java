package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class ByteArrayInputStreamExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var bytes = new byte[]{100, 101, 102, 103, 104};

        try (var bais = new ByteArrayInputStream(bytes)) {
            var value = 0;

            while ((value = bais.read()) != -1) {
                log.info("value: {}", value);
            }
        }
        log.info("end main");
    }
}
