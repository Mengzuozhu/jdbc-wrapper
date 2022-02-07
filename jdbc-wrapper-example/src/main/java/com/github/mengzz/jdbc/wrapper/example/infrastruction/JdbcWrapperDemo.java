package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Jdbc wrapper demo.
 *
 * @author mengzz
 */
public interface JdbcWrapperDemo {
    /**
     * Custom find.
     *
     * @param userQuery the user query
     * @return the list
     */
    List<User> query(UserQuery userQuery);

    /**
     * Page.
     *
     * @param userQuery the user query
     * @param pageable  the pageable
     * @return the page
     */
    Page<User> page(UserQuery userQuery, Pageable pageable);

    /**
     * Delete.
     *
     * @param userQuery the user query
     * @return the int
     */
    int delete(UserQuery userQuery);
}
