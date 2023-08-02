package com.grizz.wooman.test.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@EnableR2dbcAuditing
@EnableR2dbcRepositories(
        basePackages = "com.grizz.wooman.test.app.repository.user"
)
@TestConfiguration
public class TestR2dbcH2Config {
    @Bean
    ConnectionFactoryInitializer initializer(
            ConnectionFactory connectionFactory
    ) {
        ConnectionFactoryInitializer initializer =
                new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(
                        new ClassPathResource("sql/h2.sql")
                )
        );

        return initializer;
    }
}
