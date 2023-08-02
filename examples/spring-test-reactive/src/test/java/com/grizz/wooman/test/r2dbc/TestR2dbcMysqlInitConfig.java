package com.grizz.wooman.test.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Slf4j
@TestConfiguration
public class TestR2dbcMysqlInitConfig {
    @Bean
    public ConnectionFactoryInitializer r2dbcInitializer(
            ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer =
                new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(
                        new ClassPathResource("sql/mysql.sql")
                )
        );

        return initializer;
    }
}
