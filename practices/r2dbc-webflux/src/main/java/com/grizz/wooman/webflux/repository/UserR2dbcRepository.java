package com.grizz.wooman.webflux.repository;

import com.grizz.wooman.webflux.common.repository.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserR2dbcRepository
        extends R2dbcRepository<UserEntity, Long> {
}
