package com.grizz.wooman.test.slice;

import com.grizz.wooman.test.app.repository.user.UserEntity;
import com.grizz.wooman.test.app.repository.user.UserR2dbcRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    UserR2dbcRepository userR2dbcRepository;

    @Test
    void when_find_by_id_returns_user_mono() {
        // given
        var user = new UserEntity(
                1L, "grizz", 20, "1", "1234"
        );
        when(userR2dbcRepository.findByName(anyString()))
                .thenReturn(Mono.just(user));

        // when
        var resp = userR2dbcRepository.findByName("grizz");

        // then
        StepVerifier.create(resp)
                .assertNext(u -> {
                    assertEquals("grizz", u.getName());
                    assertEquals(20, u.getAge());
                })
                .verifyComplete();
    }
}
