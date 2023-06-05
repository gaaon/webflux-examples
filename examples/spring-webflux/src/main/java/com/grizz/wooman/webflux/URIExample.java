package com.grizz.wooman.webflux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
public class URIExample {
    @SneakyThrows
    public static void main(String[] args) {
        URI uri = new URI("http://abc:test@localhost:8080/api/hello?name=taewoo#home");

        log.info("uri.getScheme(): {}", uri.getScheme());
        log.info("uri.getSchemeSpecificPart(): {}", uri.getSchemeSpecificPart());
        log.info("uri.getAuthority(): {}", uri.getAuthority());
        log.info("uri.getUserInfo(): {}", uri.getUserInfo());
        log.info("uri.getHost(): {}", uri.getHost());
        log.info("uri.getPort(): {}", uri.getPort());
        log.info("uri.getPath(): {}", uri.getPath());
        log.info("uri.getQuery(): {}", uri.getQuery());
        log.info("uri.getFragment(): {}", uri.getFragment());
    }
}
