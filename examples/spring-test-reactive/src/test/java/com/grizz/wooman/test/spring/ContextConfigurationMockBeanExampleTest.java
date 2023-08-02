package com.grizz.wooman.test.spring;

import com.grizz.wooman.test.app.controller.GreetingController;
import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(
        classes = {
                GreetingController.class,
        }
)
public class ContextConfigurationMockBeanExampleTest {
    @Autowired
    GreetingController greetingController;

    @MockBean
    GreetingService mockGreetingService;

    @Test
    void when_request_get_then_return_greetig() {
        // given
        var who = "world";
        var message = "msg";
        when(mockGreetingService.greetingMono(anyString()))
                .thenReturn(Mono.just(message));

        // when
        var result = greetingController.greeting(who);

        // then
        StepVerifier.create(result)
                .expectNext(message)
                .verifyComplete();
    }
}
