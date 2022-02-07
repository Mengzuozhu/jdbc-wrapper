package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;

/**
 * @author mengzz
 **/
public interface JdbcWrapperProxyDemo {
    Long count(UserQuery userQuery);

    int updateName(Long id, String name);
}
