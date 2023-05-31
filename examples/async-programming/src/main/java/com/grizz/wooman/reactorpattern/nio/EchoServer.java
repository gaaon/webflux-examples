package com.grizz.wooman.reactorpattern.nio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class EchoServer {
    private static final String POISON_PILL = "BYE";

    public void start() throws IOException {
        try (final var serverSocketChannel = ServerSocketChannel.open();) {
            final var selector = Selector.open();

            serverSocketChannel.socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), 8080));
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (!Thread.interrupted()) {
                if (selector.select() == 0) {
                    // no connection yet, do some other staff
                    log.info("waiting for connection...");
                    continue;
                }
                for (final var iterator = selector.selectedKeys().iterator(); iterator.hasNext(); iterator.remove()) {
                    final var key = iterator.next();

                    if (key.isAcceptable()) {
                        final ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        final SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(1024));
                        log.info("client connected: " + client);
                    }
                    if (key.isValid() && key.isReadable()) {
                        readData(key);
                    }
                    if (key.isValid() && key.isWritable()) {
                        writeData(key);
                    }
                }
            }
        }
    }

    private void writeData(final SelectionKey key) throws IOException {
        final var channel = (SocketChannel) key.channel();
        final var buffer = (ByteBuffer) key.attachment();

        try {
            buffer.flip();
            if (!buffer.hasRemaining()) {
                return;
            }

            final var s = new String(buffer.array(), buffer.arrayOffset(), buffer.remaining()).trim();
            log.info("===> " + s);

            channel.write(buffer);

            if (s.equals(POISON_PILL)) {
                channel.close();
            }
        } finally {
            buffer.clear();
        }
    }

    private void readData(final SelectionKey key) throws IOException {
        final var buffer = ((ByteBuffer) key.attachment());
        final var channel = (SocketChannel) key.channel();
        final var read = channel.read(buffer);
        if (read <= 0) {
            return;
        }
        final var s = new String(buffer.array(), 0, read).trim();
        log.info("<=== " + s);
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

    public static void main(String[] args) throws IOException {
        try {
            new EchoServer().start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
