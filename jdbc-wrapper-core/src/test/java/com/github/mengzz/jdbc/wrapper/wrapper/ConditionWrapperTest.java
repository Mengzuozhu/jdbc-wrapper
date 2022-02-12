package com.github.mengzz.jdbc.wrapper.wrapper;

import com.github.mengzz.jdbc.wrapper.model.User;
import com.github.mengzz.jdbc.wrapper.model.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.data.relational.core.dialect.MySqlDialect;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.Table;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
class ConditionWrapperTest {
    private static final String NAME = "test";
    private static final int AGE = 20;
    private static final String REMARK = "remark";
    private static final long MONEY = 95L;
    private final Table table = SQL.table("user");
    private RendererWrapper rendererWrapper = RendererWrapper.create(MySqlDialect.INSTANCE);

    @Test
    void ignoreNullValue() {
        UserQuery userQuery = UserQuery.builder()
                .name(NAME)
                .build();
        ConditionWrapper wrapper = ConditionWrapper.of("user")
                .ignoreNullValue(true)
                .andEq(User.Fields.name, userQuery.getName())
                .andEq(User.Fields.age, userQuery.getAge());
        assertEquals("user.name = 'test'", wrapper.toString());
    }

    @Test
    void ignoreWhenEmpty() {
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .ignoreNullValue(true)
                .andEq(User.Fields.name, Collections.emptyList());
        assertEquals("1 = 1", wrapper.toString());
    }

    @Test
    void andEq() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .enableCamelColumn(true)
                .andEq(User.Fields.name, userQuery.getName())
                .andEq(User.Fields.age, userQuery.getAge());
        assertEquals("user.name = 'test' AND user.age = 20", wrapper.toString());
    }

    @Test
    void orEq() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .orEq(User.Fields.name, userQuery.getName())
                .orEq(User.Fields.age, userQuery.getAge());
        assertEquals("user.name = 'test' OR user.age = 20", wrapper.toString());
    }

    @Test
    void andNe() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andNe(User.Fields.name, userQuery.getName())
                .andNe(User.Fields.age, userQuery.getAge());
        assertEquals("user.name != 'test' AND user.age != 20", wrapper.toString());
    }

    @Test
    void andLt() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andLt(User.Fields.money, userQuery.getMoney())
                .andLt(User.Fields.age, userQuery.getAge());
        assertEquals("user.money < 95 AND user.age < 20", wrapper.toString());
    }

    @Test
    void andLe() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andLe(User.Fields.money, userQuery.getMoney())
                .andLe(User.Fields.age, userQuery.getAge());
        assertEquals("user.money <= 95 AND user.age <= 20", wrapper.toString());
    }

    @Test
    void andGt() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andGt(User.Fields.money, userQuery.getMoney())
                .andGt(User.Fields.age, userQuery.getAge());
        assertEquals("user.money > 95 AND user.age > 20", wrapper.toString());
    }

    @Test
    void andGe() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andGe(User.Fields.money, userQuery.getMoney())
                .andGe(User.Fields.age, userQuery.getAge());
        assertEquals("user.money >= 95 AND user.age >= 20", wrapper.toString());
    }

    @Test
    void like() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andLike(User.Fields.name, userQuery.getName() + "%")
                .orLike(User.Fields.remarkMsg, userQuery.getRemarkMsg());
        assertEquals("user.name LIKE 'test%' OR user.remark_msg LIKE 'remark'", wrapper.toString());
    }

    @Test
    void containing() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andContaining(User.Fields.name, userQuery.getName())
                .orContaining(User.Fields.remarkMsg, userQuery.getRemarkMsg());
        assertEquals("user.name LIKE '%test%' OR user.remark_msg LIKE '%remark%'", wrapper.toString());
    }

    @Test
    void andIn() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andIn(User.Fields.name, userQuery.getName())
                .andIn(User.Fields.age, Collections.singletonList(userQuery.getAge()));
        assertEquals("user.name IN ('test') AND user.age IN (20)", wrapper.toString());
    }

    @Test
    void andNotIn() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andNotIn(User.Fields.name, userQuery.getName())
                .andNotIn(User.Fields.age, Collections.singletonList(userQuery.getAge()));
        assertEquals("user.name NOT IN ('test') AND user.age NOT IN (20)", wrapper.toString());
    }

    @Test
    void inSubSelect() {
        Table other = SQL.table("other");
        Select subSelect = Select.builder()
                .select(other.columns(User.Fields.id))
                .from(other)
                .build();

        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andIn(User.Fields.id, subSelect);
        Select res = Select.builder()
                .select(table.columns(User.Fields.id))
                .from(table)
                .where(wrapper)
                .build();
        String render = rendererWrapper.render(res);
        assertEquals("SELECT user.id FROM user WHERE user.id IN (SELECT other.id FROM other)", render);
    }

    @Test
    void andInWhenNull() {
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .ignoreNullValue(false)
                .andIn(User.Fields.name, (Object[]) null);
        assertEquals("user.name IN ()", wrapper.toString());
    }

    @Test
    void andInWhenEmpty() {
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .ignoreNullValue(false)
                .andIn(User.Fields.name);
        assertEquals("user.name IN ()", wrapper.toString());
    }

    @Test
    void isNull() {
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andIsNull(User.Fields.name)
                .andIsNotNull(User.Fields.age);
        assertEquals("user.name IS NULL AND user.age IS NOT NULL", wrapper.toString());
    }

    @Test
    void andNest() {
        UserQuery userQuery = buildQuery();
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andEq(User.Fields.age, userQuery.getAge());
        ConditionWrapper nest = ConditionWrapper.of(table)
                .andEq(User.Fields.name, userQuery.getName())
                .andNest(wrapper);
        assertEquals("user.name = 'test' AND (user.age = 20)", nest.toString());
    }

    private UserQuery buildQuery() {
        return UserQuery.builder()
                .name(NAME)
                .remarkMsg(REMARK)
                .age(AGE)
                .money(MONEY)
                .build();
    }

}
