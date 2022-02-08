package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;

/**
 * @author mengzz
 **/
public interface JdbcWrapperProxyDemo {
    User queryForObject(UserQuery userQuery);

    Long count(UserQuery userQuery);

    int updateName(Long id, String name);
}
