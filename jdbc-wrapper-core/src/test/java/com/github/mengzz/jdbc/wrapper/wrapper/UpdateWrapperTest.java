package com.github.mengzz.jdbc.wrapper.wrapper;

import com.github.mengzz.jdbc.wrapper.model.User;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.springframework.data.relational.core.dialect.MySqlDialect;
import org.springframework.data.relational.core.sql.Assignment;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.core.sql.Update;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mengzz
 **/
class UpdateWrapperTest {
    private static final String NAME = "test";
    private static final int AGE = 20;
    private final Table table = SQL.table("user");
    private RendererWrapper rendererWrapper = RendererWrapper.create(MySqlDialect.INSTANCE);

    @Test
    void setValue() {
        Assignment assignment = UpdateWrapper.of(table)
                .setValue(User.Fields.name, NAME);
        assertThat(assignment.toString()).isEqualTo("user.name = 'test'");
    }

    @Test
    void updateWhere() {
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andEq(User.Fields.id, 1);
        Update update = UpdateWrapper.of(table)
                .set(User.Fields.name, NAME)
                .updateWhere(wrapper);
        assertThat(rendererWrapper.render(update)).isEqualTo("UPDATE user SET name = 'test' WHERE user.id = 1");
    }

    @Test
    void set() {
        List<Assignment> assignments = UpdateWrapper.of(table)
                .set(User.Fields.name, NAME)
                .set(User.Fields.age, AGE)
                .collect();
        String res = StringUtils.collectionToDelimitedString(assignments, ", ");
        assertThat(res).isEqualTo("user.name = 'test', user.age = 20");
    }

    @Test
    void setValues() {
        List<Assignment> assignments = UpdateWrapper.of(table)
                .setValues(ImmutableMap.of(User.Fields.name, NAME));
        String res = StringUtils.collectionToDelimitedString(assignments, ", ");
        assertThat(res).isEqualTo("user.name = 'test'");
    }
}
