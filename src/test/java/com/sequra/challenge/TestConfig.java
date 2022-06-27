package com.sequra.challenge;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.Clock;

@Configuration
public class TestConfig {
    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER = new PostgreSQLContainer("postgres:13.4");

    static {
        POSTGRES_SQL_CONTAINER.start();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(POSTGRES_SQL_CONTAINER.getJdbcUrl());
        dataSource.setUsername(POSTGRES_SQL_CONTAINER.getUsername());
        dataSource.setPassword(POSTGRES_SQL_CONTAINER.getPassword());

        return dataSource;
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
