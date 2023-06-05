package com.grizz.wooman.webflux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.RequestPath;

import java.net.URI;

@Slf4j
public class RequestPathExample {
    @SneakyThrows
    public static void main(String[] args) {
        URI uri = new URI("http://localhost:8080/app/api/hello?name=taewoo#home");
        RequestPath path = RequestPath.parse(uri, "/app");

        log.info("path.pathWithinApplication(): {}", path.pathWithinApplication());
        log.info("path.contextPath(): {}", path.contextPath());
    }
}
