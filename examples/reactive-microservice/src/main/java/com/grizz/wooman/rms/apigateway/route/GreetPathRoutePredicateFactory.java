package com.grizz.wooman.rms.apigateway.route;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

@Component
public class GreetPathRoutePredicateFactory
        extends AbstractRoutePredicateFactory<GreetPathRoutePredicateFactory.Config> {

    @Getter
    @Setter
    public static class Config {
        private String greeting;
    }

    public GreetPathRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("greeting");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            var path = exchange.getRequest()
                    .getPath().toString();
            var greeting = config.getGreeting();

            return path.contains(greeting);
        };
    }
}
