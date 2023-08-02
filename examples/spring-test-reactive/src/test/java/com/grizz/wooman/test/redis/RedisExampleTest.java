package com.grizz.wooman.test.redis;

import com.grizz.wooman.test.app.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

@ContextConfiguration(
        classes = TestApplication.class
)
@DataRedisTest
public class RedisExampleTest {
    @Autowired
    ReactiveStringRedisTemplate redisTemplate;

    @Test
    void test() {
        // given
        var result = redisTemplate.opsForValue()
                .set("key", "value")
                .then(redisTemplate.opsForValue().get("key"));

        // when
        StepVerifier.create(result)
                .expectNext("value")
                .verifyComplete();
    }
}
