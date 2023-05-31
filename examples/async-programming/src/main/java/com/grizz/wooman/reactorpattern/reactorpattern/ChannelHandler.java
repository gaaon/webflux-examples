package com.grizz.wooman.reactorpattern.reactorpattern;

public interface ChannelHandler {
    void read() throws Exception;
    void write() throws Exception;
}
