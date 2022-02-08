package com.github.mengzz.jdbc.wrapper.example.service;

import com.github.mengzz.jdbc.wrapper.example.BaseSpringTest;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mengzz
 **/
class UserDemoServiceTest extends BaseSpringTest {
    @Autowired
    private UserDemoService userDemoService;

    @Test
    void findByName() {
        List<User> users = userDemoService.findByName("test");
        Assertions.assertThat(users).isEmpty();
    }
}
