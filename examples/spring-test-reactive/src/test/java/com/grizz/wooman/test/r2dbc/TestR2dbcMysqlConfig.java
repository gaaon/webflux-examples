package com.grizz.wooman.test.r2dbc;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcAuditing
@EnableR2dbcRepositories(
        basePackages = "com.grizz.wooman.test.app.repository.user"
)
@TestConfiguration
public class TestR2dbcMysqlConfig {
}
