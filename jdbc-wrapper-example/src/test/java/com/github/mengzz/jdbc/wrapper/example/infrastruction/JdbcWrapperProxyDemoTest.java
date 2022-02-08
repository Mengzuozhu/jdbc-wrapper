package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.BaseSpringTest;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mengzz
 **/
class JdbcWrapperProxyDemoTest extends BaseSpringTest {
    @Autowired
    @Qualifier("jdbcWrapperProxyDemoImpl")
    private JdbcWrapperProxyDemo jdbcWrapperProxyDemo;

    @Test
    void queryForObjectShouldNull() {
        User res = jdbcWrapperProxyDemo.queryForObject(UserQuery.builder()
                .name("")
                .build());
        assertThat(res).isNull();
    }

}
