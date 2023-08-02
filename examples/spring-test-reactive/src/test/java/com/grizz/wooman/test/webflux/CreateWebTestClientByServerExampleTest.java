package com.grizz.wooman.test.webflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreateWebTestClientByServerExampleTest {
    WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Test
    void when_call_then_return_greeting() {
        // given
        String message = "hello grizz";

        // when
        webTestClient.get()
                .uri("/greeting?who=grizz")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(message);
    }
}
