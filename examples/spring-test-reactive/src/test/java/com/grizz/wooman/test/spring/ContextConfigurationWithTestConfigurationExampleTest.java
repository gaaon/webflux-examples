package com.grizz.wooman.test.spring;

import com.grizz.wooman.test.app.controller.GreetingController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(
        classes = {
                ExampleTestConfig.class
        }
)
public class ContextConfigurationWithTestConfigurationExampleTest {
    @Autowired
    GreetingController greetingController;

    @Test
    void when_request_get_then_return_greetig() {
        // given
        var who = "world";

        // when
        var result = greetingController.greeting(who);

        // then
        var expected = "hello world";
        StepVerifier.create(result)
                .expectNext(expected)
                .verifyComplete();

    }
}
