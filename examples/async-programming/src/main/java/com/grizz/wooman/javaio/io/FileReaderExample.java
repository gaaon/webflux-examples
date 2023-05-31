package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileReaderExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var file = new File(FileReaderExample.class
                .getClassLoader()
                .getResource("koreanhello.txt").getFile());

        var charset = StandardCharsets.UTF_8;
        try (var fis = new FileReader(file, charset)) {
            var value = 0;

            while ((value = fis.read()) != -1) {
                log.info("value: {}", (char)value);
            }
        }
        log.info("end main");
    }
}
