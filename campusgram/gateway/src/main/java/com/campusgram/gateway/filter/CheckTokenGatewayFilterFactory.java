package com.campusgram.gateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CheckTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckTokenGatewayFilterFactory.Config> {
    public CheckTokenGatewayFilterFactory() {
        super(Config.class);
    }
    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("tokenHeaderName");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            List<String> tokenHeaders = exchange.getRequest().getHeaders()
                    .get(config.tokenHeaderName);

            if (tokenHeaders == null || tokenHeaders.isEmpty()) {
                return chain.filter(exchange);
            }

            String tokenHeader = tokenHeaders.get(0);
            String userId = getUserIdByTokenHeader(tokenHeader);

            var nextRequest = exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", userId)
                    .build();
            var nextExchange = exchange.mutate()
                    .request(nextRequest)
                    .build();

            return chain.filter(nextExchange);
        };
    }

    private static final Map<String, String> tokenUserMap;

    static {
        tokenUserMap = Map.of("123456", "100");
    }

    @Nullable
    private String getUserIdByTokenHeader(String tokenHeader) {
        return tokenUserMap.get(tokenHeader);
    }

    @Getter
    @Setter
    public static class Config {
        private String tokenHeaderName;
    }
}
