package com.github.mengzz.jdbc.wrapper.example.service;

import com.github.mengzz.jdbc.wrapper.example.infrastruction.UserRepository;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 *
 * @author mengzz
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Find by age.
     *
     * @param age the age
     * @return the list
     */
    @Override
    public List<User> findByAge(Integer age) {
        return userRepository.findByAge(age);
    }

    /**
     * Update age.
     *
     * @param id  the id
     * @param age the age
     */
    @Override
    public void updateAge(Long id, Integer age) {
        userRepository.updateAge(id, age);
    }

    @Override
    public int updateName(Long id, String name) {
        return userRepository.updateName(id, name);
    }

    /**
     * Save.
     *
     * @param user the user
     * @return the user
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Save all.
     *
     * @param iterable the iterable
     * @return the iterable
     */
    @Override
    public Iterable<User> saveAll(Iterable<User> iterable) {
        return userRepository.batchSave(iterable);
    }

    /**
     * Custom find.
     *
     * @param userQuery the user query
     * @return the list
     */
    @Override
    public List<User> customFind(UserQuery userQuery) {
        return userRepository.query(userQuery);
    }

    @Override
    public Page<User> page(UserQuery userQuery, Pageable pageable) {
        return userRepository.page(userQuery, pageable);
    }

    @Override
    public Long count(UserQuery userQuery) {
        return userRepository.count(userQuery);
    }

    @Override
    public int delete(UserQuery userQuery) {
        return userRepository.delete(userQuery);
    }

    /**
     * Delete all.
     */
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
