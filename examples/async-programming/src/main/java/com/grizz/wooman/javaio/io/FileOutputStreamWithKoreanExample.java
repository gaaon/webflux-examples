package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class FileOutputStreamWithKoreanExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var file = new File(FileOutputStreamWithKoreanExample.class
                .getClassLoader()
                .getResource("koreanhello2.txt")
                .getFile());

        try (var fos = new FileOutputStream(file)) {
            var content = "안녕하세요 by FileWriter";

            fos.write(content.getBytes());
            fos.flush();
        }
        log.info("end main");
    }
}
