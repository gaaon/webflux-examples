package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class FileOutputStreamExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        var file = new File(FileOutputStreamExample.class
                .getClassLoader()
                .getResource("dest.txt")
                .getFile());

        try (var fos = new FileOutputStream(file)) {
            var content = "Hello World";

            fos.write(content.getBytes());
            fos.flush();
        }
        log.info("end main");
    }
}
