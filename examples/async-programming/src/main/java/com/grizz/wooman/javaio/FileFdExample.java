package com.grizz.wooman.javaio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

@Slf4j
public class FileFdExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        var file = new File(FileFdExample.class
                .getClassLoader()
                .getResource("data.txt").getFile());

        try (var fis = new FileInputStream(file)) {
            FileDescriptor fd = fis.getFD();
            Field field = FileDescriptor.class.getDeclaredField("fd");
            field.setAccessible(true);
            long value = field.getInt(fd);
            log.info("fd: {}", value);
        }
    }
}
