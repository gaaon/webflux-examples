package com.grizz.wooman.rms.stream.config;

import com.grizz.wooman.rms.stream.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class StreamFunctionConfig {
    @Bean
    public Function<Flux<String>, Flux<String>> addGreeting() {
        return new Function<Flux<String>, Flux<String>>() {
            @Override
            public Flux<String> apply(Flux<String> stringFlux) {
                return stringFlux.map(s -> "Hello " + s + "!");
            }
        };
    }

    @Bean
    public Consumer<Flux<String>> logName(LogUtil log) {
        return new Consumer<Flux<String>>() {
            @Override
            public void accept(Flux<String> nameFlux) {
                nameFlux.subscribe(s -> log.info("Name: {}", s));
            }
        };
    }

    @Bean
    public Supplier<Flux<String>> supplyNames() {
        return new Supplier<Flux<String>>() {
            @Override
            public Flux<String> get() {
                return Flux.just("grizz", "taewoo", "wooman");
            }
        };
    }

    @Bean
    public Supplier<Flux<String>> supplyNames2() {
        return new Supplier<Flux<String>>() {
            @Override
            public Flux<String> get() {
                return Mono.delay(Duration.ofSeconds(10))
                        .thenMany(Flux.just("grizz", "taewoo", "wooman"));
            }
        };
    }
}
