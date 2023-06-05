package com.grizz.wooman.webflux;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
