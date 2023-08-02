package com.grizz.wooman.rms.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddGreetingPathGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AddGreetingPathGatewayFilterFactory.Config> {
    @Getter
    @Setter
    public static class Config {
        private String greeting;
    }

    public AddGreetingPathGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("greeting");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            var path = exchange.getRequest().getPath().value();
            var nextReq = exchange.getRequest()
                    .mutate()
                    .path("/" + config.getGreeting() + path)
                    .build();
            var next = exchange.mutate()
                    .request(nextReq).build();
            return chain.filter(next);
        });
    }
}
