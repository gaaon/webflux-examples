package com.grizz.wooman.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class EventLoopMain {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
    }
}
