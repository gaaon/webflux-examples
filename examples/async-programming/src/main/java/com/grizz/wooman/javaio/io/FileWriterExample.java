package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileWriterExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var file = new File(FileWriterExample.class
                .getClassLoader()
                .getResource("koreanhello2.txt").getFile());

        var charset = StandardCharsets.UTF_8;
        try (var fis = new FileWriter(file, charset)) {
            var content = "안녕하세요 by FileWriter";
            fis.write(content);
        }
        log.info("end main");
    }
}
