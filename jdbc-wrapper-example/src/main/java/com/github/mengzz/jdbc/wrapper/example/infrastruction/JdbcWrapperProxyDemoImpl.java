package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.core.JdbcWrapper;
import com.github.mengzz.jdbc.wrapper.core.JdbcWrapperProxy;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import com.github.mengzz.jdbc.wrapper.wrapper.ConditionWrapper;
import com.github.mengzz.jdbc.wrapper.wrapper.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.core.sql.Update;
import org.springframework.stereotype.Repository;

/**
 * @author mengzz
 **/
@Repository
@RequiredArgsConstructor
public class JdbcWrapperProxyDemoImpl implements JdbcWrapperProxyDemo, JdbcWrapperProxy<User> {
    private final JdbcWrapper jdbcWrapper;

    @Override
    public JdbcWrapper getJdbcWrapper() {
        return jdbcWrapper;
    }

    @Override
    public Class<User> getGeneticClass() {
        return User.class;
    }

    @Override
    public Long count(UserQuery userQuery) {
        Table table = table();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andEq(User.Fields.name, userQuery.getName())
                .andEq(User.Fields.age, userQuery.getAge());

        return count(wrapper);
    }

    @Override
    public int updateName(Long id, String name) {
        Table table = table();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andEq(User.Fields.id, id);
        Update update = UpdateWrapper.of(table)
                .set(User.Fields.name, name)
                .updateWhere(wrapper);
        return update(update);
    }

}
