package com.grizz.wooman.webflux.repository;

import com.grizz.wooman.webflux.TestDataBuilder;
import com.grizz.wooman.webflux.common.repository.UserEntity;
import com.grizz.wooman.webflux.testconfig.TestR2dbcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestR2dbcConfig.class)
@ActiveProfiles("h2")
@DataR2dbcTest
class UserR2dbcRepositoryH2SliceTest {
    @Autowired
    UserR2dbcRepository userR2dbcRepository;

    @Autowired
    R2dbcEntityTemplate r2dbcEntityTemplate;

    @BeforeEach
    void setUp() {
        r2dbcEntityTemplate.delete(UserEntity.class)
                .all()
                .block();
    }

    @Test
    void r2dbcEntityTemplate_should_not_be_null() {
        assertNotNull(r2dbcEntityTemplate);
        assertNotNull(userR2dbcRepository);
    }

    @Test
    void when_save_then_should_return_saved_user() {
        // given
        var name = "grizz";
        var userEntity = TestDataBuilder.createUnsavedUserEntity(name);

        // when
        var result = userR2dbcRepository.save(userEntity);

        // then
        StepVerifier.create(result)
                .assertNext(createdUser -> {
                    assertNotNull(createdUser.getId());
                    assertEquals(name, createdUser.getName());
                })
                .verifyComplete();
    }

    @Test
    void when_find_all_should_returns_empty() {
        // when
        var result = userR2dbcRepository.findAll();

        // then
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void when_find_all_by_names_starts_then_returns_list() {
        // given
        var users = List.of(
                TestDataBuilder.createUnsavedUserEntity("grizz"),
                TestDataBuilder.createUnsavedUserEntity("gr"),
                TestDataBuilder.createUnsavedUserEntity("grizzzzzz"),
                TestDataBuilder.createUnsavedUserEntity("g"),
                TestDataBuilder.createUnsavedUserEntity("gro"),
                TestDataBuilder.createUnsavedUserEntity("brizz")
        );

        for (var user : users) {
            r2dbcEntityTemplate.insert(user).block();
        }

        var prefix = "gri";

        // when
        var result = userR2dbcRepository.findAllByNameStartsWith(prefix);

        // then
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }
}