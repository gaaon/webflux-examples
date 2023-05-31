package com.grizz.wooman.reactorpattern.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class EchoClient {
    private static final String POISON_PILL = "BYE";

    private final Queue<String> messageQueue = new ConcurrentLinkedQueue<>();

    private volatile boolean running = true;

    public void start() {
        final var thread = new Thread(() -> {
            try (final var reader = new BufferedReader(new InputStreamReader(System.in))) {
                for (var line = reader.readLine(); line != null; line = reader.readLine()) {
                    messageQueue.offer(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        try (final var socketChannel = SocketChannel.open();
             final var selector = Selector.open()) {
            socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8080));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);

            while (running) {
                if (selector.select(1000L) <= 0) {
                    continue;
                }
                for (var iterator = selector.selectedKeys().iterator(); iterator.hasNext(); iterator.remove()) {
                    final var key = iterator.next();
                    if (key.isReadable()) {
                        readData(key);
                    }
                    if (key.isWritable()) {
                        writeData(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key) throws Exception {
        log.info("readData");
        final var channel = (SocketChannel) key.channel();
        final var buffer = ByteBuffer.allocate(1024);
        final var read = channel.read(buffer);
        if (read <= 0) {
            return;
        }
        log.info("readData: " + read);
        final var s = new String(buffer.array(), 0, read);
        log.info("<=== " + s);
        if (POISON_PILL.equals(s.trim())) {
            running = false;
        }
    }

    private void writeData(SelectionKey key) throws Exception {
        final var channel = (SocketChannel) key.channel();
        final var line = messageQueue.poll();
        if (line == null) {
            return;
        }
        final var buffer = ByteBuffer.allocate(1024);
        buffer.put(line.getBytes());
        buffer.flip();
        channel.write(buffer);
        log.info("===> " + line);
    }

    public static void main(String[] args) {
        new EchoClient().start();
    }
}
