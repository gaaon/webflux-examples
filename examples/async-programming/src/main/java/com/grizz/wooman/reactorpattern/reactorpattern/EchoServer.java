package com.grizz.wooman.reactorpattern.reactorpattern;

import java.io.IOException;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        var reactor = new Reactor();
        reactor.bind(8080);
    }
}
