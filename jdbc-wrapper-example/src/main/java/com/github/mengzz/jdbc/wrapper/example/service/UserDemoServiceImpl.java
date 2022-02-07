package com.github.mengzz.jdbc.wrapper.example.service;

import com.github.mengzz.jdbc.wrapper.core.JdbcWrapper;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.wrapper.ConditionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mengzz
 **/
@Service
public class UserDemoServiceImpl implements UserDemoService {
    @Autowired
    private JdbcWrapper jdbcWrapper;

    @Override
    public List<User> findByName(String name) {
        Table table = jdbcWrapper.table(User.class);
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andEq(User.Fields.name, name);
        return jdbcWrapper.query(wrapper, User.class);
    }

}
