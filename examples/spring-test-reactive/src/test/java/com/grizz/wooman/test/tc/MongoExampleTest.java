package com.grizz.wooman.test.tc;

import com.grizz.wooman.test.app.TestApplication;
import com.grizz.wooman.test.app.repository.chat.ChatDocument;
import com.grizz.wooman.test.app.repository.chat.ChatMongoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ContextConfiguration(
        classes = TestApplication.class
)
@DataMongoTest(
        excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class
)
public class MongoExampleTest {
    @Autowired
    ChatMongoRepository chatMongoRepository;

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:5.0.19"))
                    .withEnv("MONGO_INITDB_DATABASE", "wooman")
                    .withExposedPorts(27017);

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.port", () -> mongoDBContainer.getMappedPort(27017));
        registry.add("spring.data.mongodb.database", () -> "wooman");
    }

    @AfterEach
    void tearDown() {
        reactiveMongoTemplate.dropCollection(
                ChatDocument.class
        ).block();
    }

    @Test
    void test() {
        // given
        var chat = new ChatDocument(
                "grizz", "wooman", "hello"
        );

        // when
        var result = chatMongoRepository.insert(chat);

        // then
        StepVerifier.create(result)
                .assertNext(document -> {
                    assertNotNull(document.getId());
                    assertEquals(
                            chat.getFrom(),
                            document.getFrom());
                    assertEquals(
                            chat.getTo(),
                            document.getTo());
                    assertEquals(
                            chat.getMessage(),
                            document.getMessage());
                })
                .verifyComplete();
    }

    @Test
    void test2() {
        // given
        var chats = List.of(
                new ChatDocument("grizz", "wooman", "hello"),
                new ChatDocument("grizz", "abc", "hoi"),
                new ChatDocument("grizz", "def", "hola")
        );

        reactiveMongoTemplate.insertAll(chats)
                .collectList()
                .block();

        // when
        var result = chatMongoRepository.findAllByFrom("grizz");

        // then
        StepVerifier.create(result)
                .expectNextCount(3)
                .verifyComplete();
    }
}
