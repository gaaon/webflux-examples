package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class BufferedInputStreamExample {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        log.info("start main");
        var file = new File(BufferedInputStreamExample.class
                .getClassLoader()
                .getResource("data.txt").getFile());

        try (var fis = new FileInputStream(file)) {
            try (var bis = new BufferedInputStream(fis)) {
                var value = 0;

                while ((value = bis.read()) != -1) {
                    log.info("value: {}", (char) value);
                }
            }
        }
        log.info("end main");
    }
}
