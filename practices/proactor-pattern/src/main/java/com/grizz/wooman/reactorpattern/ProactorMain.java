package com.grizz.wooman.reactorpattern;

// Main
public class ProactorMain {
    public static void main(String[] args) {
        new Proactor(8080).run();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
