package com.github.mengzz.jdbc.wrapper.example.infrastruction;

import com.github.mengzz.jdbc.wrapper.core.JdbcWrapper;
import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.example.model.UserQuery;
import com.github.mengzz.jdbc.wrapper.wrapper.ConditionWrapper;
import com.github.mengzz.jdbc.wrapper.wrapper.TableWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.StatementBuilder;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mengzz
 **/
@Repository
@RequiredArgsConstructor
public class JdbcWrapperDemoImpl implements JdbcWrapperDemo {
    private final JdbcWrapper jdbcWrapper;

    @Override
    public List<User> query(UserQuery userQuery) {
        return jdbcWrapper.query(getSelect(userQuery), User.class);
    }

    @Override
    public Page<User> page(UserQuery userQuery, Pageable pageable) {
        // Select select = getSelect(userQuery);
        return jdbcWrapper.page(getWrapper(userQuery), pageable, User.class);
    }

    @Override
    public int delete(UserQuery userQuery) {
        return jdbcWrapper.delete(getWrapper(userQuery), User.class);
    }

    private Select getSelect(UserQuery userQuery) {
        Table table = jdbcWrapper.table(User.class);
        ConditionWrapper wrapper = getWrapper(userQuery);
        return StatementBuilder.select()
                .select(table.columns(TableWrapper.getAllCamelFieldNames(User.class)))
                .from(table)
                .where(wrapper)
                .build();
    }

    private ConditionWrapper getWrapper(UserQuery userQuery) {
        Table table = jdbcWrapper.table(User.class);
        return ConditionWrapper.of(table)
                .andEq(User.Fields.name, userQuery.getName())
                .andEq(User.Fields.age, userQuery.getAge())
                .andLike(User.Fields.remarkMsg, userQuery.getRemarkMsg());
    }

}
