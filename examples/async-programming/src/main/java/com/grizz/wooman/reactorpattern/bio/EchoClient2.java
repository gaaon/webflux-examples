package com.grizz.wooman.reactorpattern.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class EchoClient2 {
    private static final String POISON_PILL = "BYE";

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void start() throws IOException, InterruptedException {
        final var socket = new Socket();
        socket.connect(new InetSocketAddress(8080));

        final Thread readerThread = new Thread(new ReaderTask());
        readerThread.setDaemon(true);
        readerThread.start();

        final var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        final var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        for (var msg = queue.take(); !Thread.interrupted(); msg = queue.take()) {
            log.info("===> " + msg);

            writer.write(msg);
            writer.newLine();
            writer.flush();

            final var response = reader.readLine();
            log.info("<=== " + response);

            if (response.equals(POISON_PILL + " Received")) {
                break;
            }
        }
    }

    private class ReaderTask implements Runnable {
        @Override
        public void run() {
            try (final var userReader = new BufferedReader(new InputStreamReader(System.in))) {
                for (var line = userReader.readLine(); line != null; line = userReader.readLine()) {
                    queue.put(line);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new EchoClient2().start();
    }
}
