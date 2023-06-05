package com.grizz.wooman.webflux.webhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebHandlerConfig {
    @Bean
    public WebHandler testHandler() {
        return exchange -> {
            final ServerHttpRequest request = exchange.getRequest();
            final ServerHttpResponse response = exchange.getResponse();

            String nameQuery = request.getQueryParams().getFirst("name");
            String name = nameQuery == null ? "world" : nameQuery;

            String content = "Hello " + name;
            log.info("responseBody: {}", content);
            Mono<DataBuffer> responseBody = Mono.just(
                    response.bufferFactory()
                            .wrap(content.getBytes())
            );

            response.getHeaders()
                    .add("Content-Type", "text/plain");
            return response.writeWith(responseBody);
        };
    }
}
