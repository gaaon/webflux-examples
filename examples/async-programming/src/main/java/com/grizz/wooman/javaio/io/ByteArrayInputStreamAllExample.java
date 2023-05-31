package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class ByteArrayInputStreamAllExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var bytes = new byte[]{100, 101, 102, 103, 104};

        try (var bais = new ByteArrayInputStream(bytes)) {
            var values = bais.readAllBytes();
            log.info("values: {}", values);
        }
        log.info("end main");
    }
}
