package com.grizz.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    @Bean
    public WebClient weatherWebClient() {
        return WebClient.create("http://localhost:8081");
    }
}
