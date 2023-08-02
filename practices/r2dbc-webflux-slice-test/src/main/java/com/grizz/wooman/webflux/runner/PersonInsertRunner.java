package com.grizz.wooman.webflux.runner;

import com.grizz.wooman.webflux.common.repository.UserEntity;
import com.grizz.wooman.webflux.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

@Slf4j
@RequiredArgsConstructor
//@Component
public class PersonInsertRunner implements CommandLineRunner {
    private final UserR2dbcRepository userR2dbcRepository;

    @Override
    public void run(String... args) throws Exception {
        var newUser = new UserEntity("taewoo", 20, "1", "1q2w3e4r!");
        var savedUser = userR2dbcRepository.save(newUser).block();
        log.info("savedUser: {}", savedUser);
    }
}
