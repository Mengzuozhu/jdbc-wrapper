package com.github.mengzz.jdbc.wrapper.example.service;

import com.github.mengzz.jdbc.wrapper.example.model.User;

import java.util.List;

/**
 * @author mengzz
 **/
public interface UserDemoService {
    List<User> findByName(String name);
}
