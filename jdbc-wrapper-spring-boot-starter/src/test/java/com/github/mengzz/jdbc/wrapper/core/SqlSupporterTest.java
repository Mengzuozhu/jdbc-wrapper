package com.github.mengzz.jdbc.wrapper.core;

import com.github.mengzz.jdbc.wrapper.model.User;
import com.github.mengzz.jdbc.wrapper.wrapper.ConditionWrapper;
import com.github.mengzz.jdbc.wrapper.wrapper.RendererWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.dialect.MySqlDialect;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mengzz
 **/
class SqlSupporterTest {
    private SqlSupporter supporter = SqlSupporter.of(new JdbcMappingContext());
    private RendererWrapper rendererWrapper = RendererWrapper.create(MySqlDialect.INSTANCE);
    private ConditionWrapper wrapper = ConditionWrapper.of(supporter.table(User.class))
            .andEq(User.Fields.name, "test");

    @Test
    void table() {
        assertThat(supporter.table(User.class)).isNotNull();
    }

    @Test
    void buildSelect() {
        String str = rendererWrapper.render(supporter.buildSelect(wrapper, User.class));
        assertThat(str).isEqualTo("SELECT user.id, user.name FROM user WHERE user.name = 'test'");
    }

    @Test
    void pageSelect() {
        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.DESC, User.Fields.id);
        String str = rendererWrapper.render(supporter.pageSelect(wrapper, pageable, User.class));
        String expected = "SELECT user.id, user.name FROM user WHERE user.name = 'test' ORDER BY `id` DESC LIMIT 0, 5";
        assertThat(str).isEqualTo(expected);
    }

    @Test
    void countSelect() {
        String str = rendererWrapper.render(supporter.countSelect(wrapper, User.class));
        assertThat(str).isEqualTo("SELECT COUNT(*) FROM user WHERE user.name = 'test'");

    }

    @Test
    void buildDelete() {
        String str = rendererWrapper.render(supporter.buildDelete(wrapper, User.class));
        assertThat(str).isEqualTo("DELETE FROM user WHERE user.name = 'test'");
    }
}
