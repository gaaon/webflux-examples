package com.grizz.wooman.webflux.repository;

import com.grizz.wooman.webflux.common.repository.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface UserR2dbcRepository
        extends R2dbcRepository<UserEntity, Long> {
    Flux<UserEntity> findAllByNameStartsWith(String prefix);
}
