package com.grizz.wooman.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class WebClientCreateExample {
    public static void main(String[] args) {
        WebClient webClient = WebClient.create();

        WebClient.Builder webClientBuilder = WebClient.builder();

        WebClient webClientWithUrl = WebClient.create("https://fastcampus.co.kr");

    }
}
