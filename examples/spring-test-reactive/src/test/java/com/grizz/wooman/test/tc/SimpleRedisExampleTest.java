package com.grizz.wooman.test.tc;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import reactor.test.StepVerifier;

public class SimpleRedisExampleTest {
    @Test
    void testRedisContainer() {
        // given
        GenericContainer redisContainer = new GenericContainer("redis:5.0.3-alpine")
                .withExposedPorts(6379);
        redisContainer.start();

        System.out.println(redisContainer.getExposedPorts());
        System.out.println(redisContainer.getPortBindings());
        System.out.println(redisContainer.getMappedPort(6379));
        System.out.println(redisContainer.getFirstMappedPort());

        var redisUri = RedisURI.create(
                redisContainer.getHost(),
                redisContainer.getMappedPort(6379)
        );
        var connection = RedisClient.create(redisUri).connect();
        var command = connection.reactive();

        // when
        var result = command.set("name", "grizz")
                .then(command.get("name"));

        // then
        StepVerifier.create(result)
                .expectNext("grizz")
                .verifyComplete();

        // finally
        connection.close();
        redisContainer.stop();
    }
}
