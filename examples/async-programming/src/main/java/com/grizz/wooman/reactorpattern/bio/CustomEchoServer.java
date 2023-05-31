package com.grizz.wooman.reactorpattern.bio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class CustomEchoServer {
    private static final String POISON_PILL = "BYE";
    private static Executor executor = Executors.newCachedThreadPool();

    public void start() throws IOException {
        try (var serverSocket = new ServerSocket();) {
            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 8080));

            while (true) {
                var socket = serverSocket.accept();
                log.info("new connection: " + socket);
                executor.execute(new SocketHandler(socket));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new CustomEchoServer().start();
    }

    @RequiredArgsConstructor
    class SocketHandler implements Runnable {
        private final Socket socket;

        @Override
        public void run() {
            // try-with-resources with socket, writer, and reader
            try (final Socket ignored = socket;
                 final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    log.info("<=== " + line);
                    writer.write(line + " Received");
                    writer.newLine();
                    writer.flush();
                    log.info("===> " + line);
                    if (line.equals(POISON_PILL)) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.info("closing socket " + socket + ": " + socket.isClosed());
            }
        }
    }
}
