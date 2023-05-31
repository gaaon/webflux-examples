package com.grizz.wooman.javaio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ObjectInputStreamExample {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        log.info("start main");
        try (var ois = new ObjectInputStream(
                new ByteArrayInputStream(getBytes()))
        ) {
            var user = (User) ois.readObject();
            log.info("user: {}", user);
        }
        log.info("end main");
    }

    private static byte[] getBytes() throws IOException {
        var user = new User("Taewoo", 10);

        try (var baos = new ByteArrayOutputStream();
             var oos = new ObjectOutputStream(baos)) {
            oos.writeObject(user);
            return baos.toByteArray();
        }
    }
}
