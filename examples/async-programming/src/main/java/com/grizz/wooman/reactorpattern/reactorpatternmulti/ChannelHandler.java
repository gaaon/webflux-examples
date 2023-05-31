package com.grizz.wooman.reactorpattern.reactorpatternmulti;

public interface ChannelHandler {
    void read() throws Exception;
    void write() throws Exception;
}
