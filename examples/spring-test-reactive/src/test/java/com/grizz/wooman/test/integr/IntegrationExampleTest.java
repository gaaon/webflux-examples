package com.grizz.wooman.test.integr;

import com.grizz.wooman.test.app.TestApplication;
import com.grizz.wooman.test.app.repository.greeting.GreetingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("integration")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = TestApplication.class
)
public class IntegrationExampleTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    R2dbcEntityTemplate r2dbcEntityTemplate;

    @BeforeEach
    void setUp() {
        r2dbcEntityTemplate.delete(GreetingEntity.class)
                .all()
                .block();
    }

    @Test
    void when_no_saved_greeting_should_return_default_greeting() {
        // given
        var who = "grizz";

        // when, then
        webTestClient.get()
                .uri("/greeting/diff?who={who}", who)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("hello " + who);
    }

    @Test
    void when_saved_greeting_found_should_return_different_greeting() {
        // given
        var who = "grizz";
        var greeting = "hola";
        r2dbcEntityTemplate.insert(
                new GreetingEntity(greeting, who)
        ).block();

        // when, then
        webTestClient.get()
                .uri("/greeting/diff?who={who}", who)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo( greeting + " " + who);
    }
}
