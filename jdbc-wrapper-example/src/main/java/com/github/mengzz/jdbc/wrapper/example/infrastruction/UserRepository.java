package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 *
 * @author mengzz
 */
public interface UserRepository {
    /**
     * Save.
     *
     * @param user the user
     * @return the user
     */
    User save(User user);

    /**
     * Batch save.
     *
     * @param entities the entities
     * @return the iterable
     */
    Iterable<User> batchSave(Iterable<User> entities);

    /**
     * Find by age.
     *
     * @param age the name
     * @return the list
     */
    List<User> findByAge(Integer age);

    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User> findById(Long id);

    /**
     * Update age.
     *
     * @param id  the id
     * @param age the age
     */
    void updateAge(Long id, Integer age);

    int updateName(Long id, String name);

    /**
     * Custom find.
     *
     * @param userQuery the user query
     * @return the list
     */
    List<User> query(UserQuery userQuery);

    Page<User> page(UserQuery userQuery, Pageable pageable);

    /**
     * Count.
     *
     * @param userQuery the user query
     * @return the long
     */
    Long count(UserQuery userQuery);

    int delete(UserQuery userQuery);

    /**
     * Delete all.
     */
    void deleteAll();
}
