package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.BaseSpringTest;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mengzz
 **/
class JdbcWrapperProxyDemoTest extends BaseSpringTest {
    @Autowired
    @Qualifier("jdbcWrapperProxyDemoImpl")
    private JdbcWrapperProxyDemo jdbcWrapperProxyDemo;

    @Test
    void queryFirst() {
        User user = save();
        User res = jdbcWrapperProxyDemo.queryFirst(UserQuery.builder()
                .age(user.getAge())
                .build());
        assertThat(res).isNotNull();
    }

    @Test
    void queryForFirstShouldNull() {
        User res = jdbcWrapperProxyDemo.queryFirst(getQuery());
        assertThat(res).isNull();
    }

    @Test
    void queryForObjectShouldNull() {
        User res = jdbcWrapperProxyDemo.queryForObject(getQuery());
        assertThat(res).isNull();
    }

    @Test
    void queryForMapShouldNull() {
        Map<String, Object> res = jdbcWrapperProxyDemo.queryForMap(getQuery());
        assertThat(res).isNull();
    }

    private UserQuery getQuery() {
        return UserQuery.builder()
                .name("")
                .build();
    }

}
