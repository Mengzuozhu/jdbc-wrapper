package com.github.mengzz.jdbc.wrapper.example;

import com.github.mengzz.jdbc.wrapper.example.infrastruction.UserRepository;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author mengzz
 **/
@SpringBootTest
@ExtendWith(SpringExtension.class)
// @ActiveProfiles(value = "mysql")
@ActiveProfiles(value = "h2")
public class BaseSpringTest {
    protected static final Integer AGE = 24;
    protected static final Long BATCH_SIZE = 10L;
    protected static final String TEST = "test";
    @Autowired
    private UserRepository userRepository;

    protected Iterable<User> saveAll() {
        List<User> users = LongStream.range(0, BATCH_SIZE)
                .mapToObj(this::buildUser)
                .collect(Collectors.toList());
        return userRepository.batchSave(users);
    }

    protected User save() {
        return userRepository.save(buildUser());
    }

    protected User buildUser() {
        return buildUser(1);
    }

    protected User buildUser(long i) {
        return User.builder()
                .name(TEST + i)
                .remarkMsg("jdbc wrapper demo" + i)
                .age(AGE)
                .build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @SpringBootApplication(scanBasePackages = "com.github.mengzz.jdbc.wrapper.example")
    static class InnerConfig {
    }

}
