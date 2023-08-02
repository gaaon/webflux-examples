package com.grizz.wooman.test.webflux;

import com.grizz.wooman.test.app.controller.GreetingController;
import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
                GreetingController.class,
        }
)
public class WebTestClientHeaderExampleTest {
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

    @Test
    void when_call_then_return_greeting() {
        // given
        String message = "msg";
        when(mockGreetingService.greetingMono(anyString()))
                .thenReturn(Mono.just(message));

        // when
        webTestClient.get()
                .uri("/greeting/header?who=grizz&age=20")
                .exchange()
                .expectHeader()
                .contentType("text/plain;charset=UTF-8")
                .expectHeader()
                .exists("X-WHO")
                .expectHeader()
                .doesNotExist("X-EMAIL")
                .expectHeader()
                .value("X-WHO", header -> {
                    assertEquals("grizz", header);
                })
                .expectHeader()
                .valueEquals("X-AGE", 20L);
    }
}
