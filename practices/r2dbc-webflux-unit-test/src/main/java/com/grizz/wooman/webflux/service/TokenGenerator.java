package com.grizz.wooman.webflux.service;

public class TokenGenerator {
    public String execute() {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char item = (char)('A' + (Math.random() * 26));
            token.append(item);
        }

        return token.toString();
    }
}
