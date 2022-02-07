package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface User jdbc repository.
 *
 * @author mengzz
 */
public interface UserJdbcRepository extends CrudRepository<User, Long>, UserRepository,
        JdbcWrapperDemo, JdbcWrapperProxyDemo {

    /**
     * Batch save.
     *
     * @param entities the entities
     * @return the iterable
     */
    @Override
    default Iterable<User> batchSave(Iterable<User> entities) {
        // 为避免方法重名，内部再调用CrudRepository的方法saveAll
        return saveAll(entities);
    }

    /**
     * Find by age.
     * use @Query
     *
     * @param age the age
     * @return the list
     */
    @Query("SELECT * FROM user WHERE age = :age")
    @Override
    List<User> findByAge(Integer age);

    /**
     * Update age.
     * use @Query and @Modifying
     *
     * @param id  the id
     * @param age the age
     */
    @Override
    @Modifying
    @Query("UPDATE user SET age = :age WHERE id = :id")
    void updateAge(Long id, Integer age);

}
