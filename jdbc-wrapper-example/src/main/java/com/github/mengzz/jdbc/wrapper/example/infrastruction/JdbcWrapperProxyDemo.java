package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;

import java.util.Map;

/**
 * @author mengzz
 **/
public interface JdbcWrapperProxyDemo {
    User queryFirst(UserQuery userQuery);

    User queryForObject(UserQuery userQuery);

    Map<String, Object> queryForMap(UserQuery userQuery);

    Long count(UserQuery userQuery);

    int updateName(Long id, String name);
}
