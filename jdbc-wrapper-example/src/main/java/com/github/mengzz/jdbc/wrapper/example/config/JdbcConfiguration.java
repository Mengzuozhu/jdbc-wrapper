package com.github.mengzz.jdbc.wrapper.example.config;

import com.github.mengzz.jdbc.wrapper.interceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/**
 * @author mengzz
 **/
@Configuration
@EnableJdbcAuditing
@EnableJdbcRepositories(basePackages = "com.github.mengzz.jdbc.wrapper.example.infrastruction")
public class JdbcConfiguration {
    @Bean
    public Interceptor customWhereVisitor() {
        return null;
    }
}
