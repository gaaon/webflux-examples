package com.grizz.wooman.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class WebSocketConfig {
    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    public WebSocketService webSocketService() {
        HandshakeWebSocketService webSocketService = new HandshakeWebSocketService() {
            @Override
            public Mono<Void> handleRequest(ServerWebExchange exchange, WebSocketHandler handler) {
                String iam = exchange.getRequest()
                        .getHeaders()
                        .getFirst("X-I-AM");

                if (iam == null) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                return exchange.getSession()
                        .flatMap(session -> {
                            session.getAttributes().put("iam", iam);
                            return super.handleRequest(exchange, handler);
                        });

            }
        };

        webSocketService.setSessionAttributePredicate(s -> true);
        return webSocketService;
    }
}
