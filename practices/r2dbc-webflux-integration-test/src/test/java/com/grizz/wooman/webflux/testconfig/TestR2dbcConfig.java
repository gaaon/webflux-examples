package com.grizz.wooman.webflux.testconfig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@EnableR2dbcAuditing
@TestConfiguration
public class TestR2dbcConfig {
}
