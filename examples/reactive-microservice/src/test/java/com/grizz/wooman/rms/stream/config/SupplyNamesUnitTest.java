package com.grizz.wooman.rms.stream.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SupplyNamesUnitTest {
    StreamFunctionConfig streamFunctionConfig =
            new StreamFunctionConfig();

    @Test
    public void supplyNames() {
        // when
        var nameFlux = streamFunctionConfig.supplyNames()
                .get();

        // then
        StepVerifier.create(nameFlux)
                .expectNext("grizz")
                .expectNext("taewoo")
                .expectNext("wooman")
                .verifyComplete();
    }
}
