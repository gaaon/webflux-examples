package com.grizz.wooman.test.app.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GreetingControllerAdvice {
    @ExceptionHandler
    public String handleGreetingException(GreetingException e) {
        return "GreetingException";
    }
}
