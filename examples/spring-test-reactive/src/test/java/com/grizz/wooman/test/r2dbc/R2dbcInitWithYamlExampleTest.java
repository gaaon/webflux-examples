package com.grizz.wooman.test.r2dbc;

import com.grizz.wooman.test.app.TestApplication;
import com.grizz.wooman.test.app.repository.user.UserEntity;
import com.grizz.wooman.test.app.repository.user.UserR2dbcRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles(value = {"mysql", "mysql-init"})
@Import(TestR2dbcMysqlConfig.class)
@ContextConfiguration(
        classes = TestApplication.class
)
@DataR2dbcTest
public class R2dbcInitWithYamlExampleTest {
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

