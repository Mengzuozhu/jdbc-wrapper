package com.github.mengzz.jdbc.wrapper.example.service;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
@SpringBootTest
@ExtendWith(SpringExtension.class)
// @ActiveProfiles(value = "mysql")
@ActiveProfiles(value = "h2")
class UserDemoServiceTest {
    @Autowired
    private UserDemoService userDemoService;

    @Test
    void findByName() {
        List<User> users = userDemoService.findByName("test");
    }
}
