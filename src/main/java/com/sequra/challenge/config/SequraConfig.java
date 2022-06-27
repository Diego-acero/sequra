package com.sequra.challenge.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.inject.Singleton;

@Configuration
@NoArgsConstructor
@ComponentScan(
        basePackages = "com.sequra.challenge",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Singleton.class)
)
@EnableJpaRepositories(basePackages = "com.sequra.challenge.domain")
@AutoConfiguration
public class SequraConfig {

}
