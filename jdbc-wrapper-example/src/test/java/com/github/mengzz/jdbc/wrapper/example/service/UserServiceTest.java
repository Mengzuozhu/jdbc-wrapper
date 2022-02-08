package com.github.mengzz.jdbc.wrapper.example.service;

import com.github.mengzz.jdbc.wrapper.example.BaseSpringTest;
import com.github.mengzz.jdbc.wrapper.example.config.CustomWhereInterceptor;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
class UserServiceTest extends BaseSpringTest {
    private static final Integer AGE = 24;
    private static final Long BATCH_SIZE = 10L;
    private static final String TEST = "test";
    @Autowired
    private UserService userService;

    @AfterEach
    void tearDown() {
        userService.deleteAll();
    }

    @Test
    void findById() {
        User user = userService.save(buildUser());
        Optional<User> result = userService.findById(user.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void findByAge() {
        User user = userService.save(buildUser());
        List<User> users = userService.findByAge(user.getAge());
        assertEquals(1, users.size());
        assertEquals(user.getAge(), users.get(0).getAge());
    }

    @Test
    void updateAge() {
        User user = userService.save(buildUser());
        Integer age = 26;
        userService.updateAge(user.getId(), age);
        Optional<User> result = userService.findById(user.getId());
        assertTrue(result.isPresent());
        assertEquals(age, result.map(User::getAge).get());
    }

    @Test
    void updateName() {
        User user = userService.save(buildUser());
        String name = "new";
        userService.updateName(user.getId(), name);
        Optional<User> result = userService.findById(user.getId());
        assertTrue(result.isPresent());
        assertEquals(name, result.map(User::getName).get());
    }

    @Test
    void save() {
        User user = userService.save(buildUser());
        assertNotNull(user);
    }

    @Test
    void saveAllTest() {
        Iterable<User> result = saveAll();
        assertTrue(StreamSupport.stream(result.spliterator(), false)
                .map(User::getName)
                .allMatch(name -> name.startsWith(TEST))
        );
    }

    @Test
    void customFindShouldOne() {
        saveAll();
        String name = TEST + 0;
        List<User> users = userService.customFind(UserQuery.builder()
                .name(name)
                .age(AGE)
                .build());
        assertEquals(1, users.size());
        assertEquals(AGE, users.get(0).getAge());
        assertEquals(name, users.get(0).getName());
    }

    @Test
    void customFindShouldAll() {
        saveAll();
        List<User> users = userService.customFind(UserQuery.builder()
                .build());
        assertEquals(BATCH_SIZE.intValue(), users.size());
    }

    @Test
    void likeShouldOne() {
        saveAll();
        List<User> users = userService.customFind(UserQuery.builder()
                .remarkMsg("%demo1%")
                .build());
        assertEquals(1, users.size());
    }

    @Test
    void pageShouldAll() {
        saveAll();
        int size = 5;
        Page<User> users = userService.page(UserQuery.builder()
                .age(AGE)
                .build(), PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, User.Fields.name)));
        assertEquals(size, users.getNumberOfElements());
        assertEquals(BATCH_SIZE.longValue(), users.getTotalElements());
    }

    @Test
    void countShouldAll() {
        saveAll();
        Long count = userService.count(UserQuery.builder()
                .build());
        assertEquals(BATCH_SIZE, count);
    }

    @Test
    void countShouldOne() {
        saveAll();
        String name = TEST + 0;
        Long count = userService.count(UserQuery.builder()
                .name(name)
                .age(AGE)
                .build());
        assertEquals(1, count.intValue());
    }

    @Test
    void deleteShouldOne() {
        saveAll();
        String name = TEST + 0;
        UserQuery query = UserQuery.builder()
                .name(name)
                .age(AGE)
                .build();
        int count = userService.delete(query);
        List<User> users = userService.customFind(query);
        assertEquals(1, count);
        assertEquals(0, users.size());
    }

    @Test
    void intercept() {
        saveAll();
        List<User> users = userService.customFind(UserQuery.builder()
                .remarkMsg("%demo1%")
                .build());
        Assert.assertNotNull(CustomWhereInterceptor.getRecord());
    }

    private Iterable<User> saveAll() {
        List<User> users = LongStream.range(0, BATCH_SIZE)
                .mapToObj(this::buildUser)
                .collect(Collectors.toList());
        return userService.saveAll(users);
    }

    private User buildUser() {
        return buildUser(1);
    }

    private User buildUser(long i) {
        return User.builder()
                .name(TEST + i)
                .remarkMsg("spring jdbc demo" + i)
                .age(AGE)
                .build();
    }

}
