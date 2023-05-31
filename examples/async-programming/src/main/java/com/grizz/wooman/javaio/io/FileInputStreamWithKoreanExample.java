package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class FileInputStreamWithKoreanExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var file = new File(FileInputStreamWithKoreanExample.class
                .getClassLoader()
                .getResource("koreanhello.txt").getFile());

        try (var fis = new FileInputStream(file)) {
            var value = 0;

            while ((value = fis.read()) != -1) {
                log.info("value: {}", (char)value);
            }
        }
        log.info("end main");
    }
}
