package com.github.mengzz.jdbc.wrapper.example.config;

import com.github.mengzz.jdbc.wrapper.example.BaseSpringTest;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import com.github.mengzz.jdbc.wrapper.example.service.UserService;
import com.github.mengzz.jdbc.wrapper.interceptor.SqlInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mengzz
 **/
class CustomInterceptorTest extends BaseSpringTest {
    @Autowired
    private UserService userService;

    @Test
    void intercept() {
        saveAll();
        List<User> users = userService.customFind(UserQuery.builder()
                .remarkMsg("%demo1%")
                .build());
        assertThat(users.size()).isEqualTo(1);
        assertThat(CustomInterceptor.getRecord().toString()).contains("user.tenant = 'test'");
    }

    @TestConfiguration
    static class InterceptConfig {
        @Bean
        public SqlInterceptor customWhereVisitor() {
            return new CustomInterceptor();
        }
    }

}
