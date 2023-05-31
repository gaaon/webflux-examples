package com.grizz.wooman.javaio.io;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class BufferedOutputStreamExample {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        log.info("start main");
        var file = new File(BufferedOutputStreamExample.class
                .getClassLoader()
                .getResource("dest2.txt")
                .getFile());

        var fos = new FileOutputStream(file);
        try (var bos = new BufferedOutputStream(fos)) {
            var content = "Hello World in buffered";

            bos.write(content.getBytes());
            bos.flush();
        }
        log.info("end main");
    }
}
