package com.grizz.wooman.rms.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GreetingGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .then(Mono.just(exchange))
                .then(Mono.fromRunnable(() -> {
                    var response = exchange.getResponse();
                    if (!response.isCommitted()) {
                        response.getHeaders()
                                .add("X-Hello", "World");
                    }
                }));
    }
}
