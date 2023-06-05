package com.grizz.wooman.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@Configuration
public class SimpleUrlHandlerMappingConfig {
    @Bean
    SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        Map<String, Object> urlMap = Map.of(
                "/suhm-greet", new GreetWebHandler(),
                "/ws", new GreetWebSocketHandler(),
                "/echo", new EchoWebSocketHandler()
        );
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(1);
        mapping.setUrlMap(urlMap);

        return mapping;
    }
}
