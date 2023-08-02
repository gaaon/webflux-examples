package com.grizz.wooman.webflux.service;

import com.grizz.wooman.webflux.common.repository.AuthEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveSelectOperation;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    R2dbcEntityTemplate mockR2dbcEntityTemplate;

    @InjectMocks
    AuthService authService;

    @Mock
    ReactiveSelectOperation.ReactiveSelect<AuthEntity> mockReactiveSelect;

    @Mock
    ReactiveSelectOperation.TerminatingSelect<AuthEntity> mockTerminatingSelect;

    @Captor
    ArgumentCaptor<Query> queryArgumentCaptor;

    @Test
    void authServiceNotNull() {
        assertNotNull(authService);
        assertTrue(MockUtil.isMock(mockR2dbcEntityTemplate));
    }

    @Nested
    class GetNameByToken {
        String token;

        @BeforeEach
        void setup() {
            lenient().when(mockR2dbcEntityTemplate.select(AuthEntity.class))
                    .thenReturn(mockReactiveSelect);

            lenient().when(mockReactiveSelect.matching(any()))
                    .thenReturn(mockTerminatingSelect);

            token = "valid_token";
        }

        @Test
        void when_auth_entity_is_empty_then_returns_empty_mono() {
            // given
            when(mockTerminatingSelect.one())
                    .thenReturn(Mono.empty());

            // when
            var result = authService.getNameByToken(token);

            // then
            StepVerifier.create(result)
                    .verifyComplete();

            verify(mockReactiveSelect).matching(queryArgumentCaptor.capture());

            var actualQueryOptional = queryArgumentCaptor.getValue();
            assertTrue(actualQueryOptional.getCriteria().isPresent());
            var actualQuery = actualQueryOptional.getCriteria().get();

            assertEquals(actualQuery.getColumn().getReference(), "token");
            assertEquals(actualQuery.getValue(), token);
        }

        @Test
        void when_auth_entity_is_not_empty_then_returns_name() {
            // given
            var userId = 100L;
            AuthEntity authEntity = new AuthEntity(
                    1L,
                    userId,
                    token
            );

            when(mockTerminatingSelect.one())
                    .thenReturn(Mono.just(authEntity));

            // when
            var result = authService.getNameByToken(token);

            // then
            StepVerifier.create(result)
                    .expectNext(String.valueOf(userId))
                    .verifyComplete();
        }

        @Test
        void when_token_is_null_then_returns_returns_mono_error() {
            // given
            token = null;

            // when
            var result = authService.getNameByToken(token);

            // then
            StepVerifier.create(result)
                    .verifyErrorSatisfies(e -> {
                        assertInstanceOf(IllegalArgumentException.class, e);
                        assertEquals("token is invalid", e.getMessage());
                    });

            verify(mockR2dbcEntityTemplate, never()).select(any());
        }

        @Test
        void when_token_is_empty_then_returns_returns_mono_error() {
            // given
            token = "";

            // when
            var result = authService.getNameByToken(token);

            // then
            StepVerifier.create(result)
                    .verifyErrorSatisfies(e -> {
                        assertInstanceOf(IllegalArgumentException.class, e);
                        assertEquals("token is invalid", e.getMessage());
                    });
        }

        @Test
        void when_token_is_admin_then_returns_admin() {
            // given
            token = "admin";

            // when
            var result = authService.getNameByToken(token);

            // then
            StepVerifier.create(result)
                    .expectNext("admin")
                    .verifyComplete();
        }
    }
}