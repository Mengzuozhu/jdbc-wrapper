package com.github.mengzz.jdbc.wrapper.example.service;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author mengzz
 **/
public interface UserService {
    Optional<User> findById(Long id);

    List<User> findByAge(Integer age);

    void updateAge(Long id, Integer age);

    int updateName(Long id, String name);

    User save(User user);

    Iterable<User> saveAll(Iterable<User> iterable);

    List<User> customFind(UserQuery userQuery);

    Page<User> page(UserQuery userQuery, Pageable pageable);

    Long count(UserQuery userQuery);

    int delete(UserQuery userQuery);

    void deleteAll();
}
