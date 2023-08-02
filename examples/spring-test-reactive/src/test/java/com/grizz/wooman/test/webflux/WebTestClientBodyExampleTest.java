package com.grizz.wooman.test.webflux;

import com.grizz.wooman.test.app.controller.GreetingController;
import com.grizz.wooman.test.app.service.GreetingService;
import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
                GreetingController.class,
        }
)
public class WebTestClientBodyExampleTest {
    @MockBean
    GreetingService mockGreetingService;

    @Autowired
    GreetingController greetingController;

    WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToController(
                        greetingController
                ).build();
    }

    @EqualsAndHashCode
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class GreetingResponse {
        private String message;
        private String who;
        private int age;
    }

    @Test
    void when_call_then_return_greeting() {
        // given
        String message = "msg";
        when(mockGreetingService.greetingMono(anyString()))
                .thenReturn(Mono.just(message));

        // when
        var expected = new GreetingResponse(message, "grizz", 20);

        webTestClient.get()
                .uri("/greeting/body?who=grizz&age=20")
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(GreetingResponse.class)
                .isEqualTo(expected)
                .value(greetingResponse -> {
                    assertTrue(greetingResponse.age > 0);
                });
    }
}
