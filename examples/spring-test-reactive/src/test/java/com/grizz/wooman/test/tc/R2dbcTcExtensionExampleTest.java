package com.grizz.wooman.test.tc;

import com.grizz.wooman.test.app.TestApplication;
import com.grizz.wooman.test.app.repository.user.UserEntity;
import com.grizz.wooman.test.app.repository.user.UserR2dbcRepository;
import com.grizz.wooman.test.r2dbc.TestR2dbcMysqlConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ActiveProfiles("mysql-init")
@Import(TestR2dbcMysqlConfig.class)
@ContextConfiguration(
        classes = TestApplication.class
)
@DataR2dbcTest
public class R2dbcTcExtensionExampleTest {
    @Container
    static MySQLContainer mySQLContainer =
            new MySQLContainer("mysql:8.0.31")
                    .withDatabaseName("wooman");

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        var port = mySQLContainer.getMappedPort(3306);
        var url = String.format(
                "r2dbc:mysql://localhost:%d/wooman", port);
        registry.add("spring.r2dbc.url", () -> url);
        registry.add("spring.r2dbc.username",
                mySQLContainer::getUsername);
        registry.add("spring.r2dbc.password",
                mySQLContainer::getPassword);
    }

    @Autowired
    UserR2dbcRepository userR2dbcRepository;

    @Autowired
    DatabaseClient databaseClient;

    @Autowired
    R2dbcEntityTemplate r2dbcEntityTemplate;

    @Test
    void when_save_then_it_should_save_it() {
        // given
        UserEntity user = new UserEntity(
                "grizz", 30, "1234", "1q2w3e4r!"
        );

        // when
        var result = userR2dbcRepository.save(user);

        // then
        StepVerifier.create(result)
                .assertNext(it -> {
                    assertEquals(user.getName(), it.getName());
                    assertEquals(user.getAge(), it.getAge());
                    assertEquals(
                            user.getProfileImageId(),
                            it.getProfileImageId());
                    assertEquals(
                            user.getPassword(),
                            it.getPassword());
                    assertNotNull(it.getId());
                })
                .verifyComplete();
    }

    @Test
    void when_find_by_name_should_return_result() {
        // given
        var name = "grizz";
        UserEntity user = new UserEntity(
                name, 30, "1234", "1q2w3e4r!"
        );
        r2dbcEntityTemplate.insert(UserEntity.class)
                .using(user)
                .block();

        // when
        var result = userR2dbcRepository.findByName(name);

        // then
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @AfterEach
    void tearDown() {
        r2dbcEntityTemplate.delete(UserEntity.class)
                .all()
                .block();
    }
}

