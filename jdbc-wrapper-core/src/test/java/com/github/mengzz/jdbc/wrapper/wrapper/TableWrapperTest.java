package com.github.mengzz.jdbc.wrapper.wrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mengzz
 **/
class TableWrapperTest {

    @Test
    void getAllCamelFieldNames() {
        List<String> fieldNames = TableWrapper.getAllCamelFieldNames(Demo.class);
        assertThat(fieldNames).contains("id", "modify_time");
    }

    @Test
    void getCamelFieldNamesExclude() {
        List<String> fieldNames = TableWrapper.getCamelFieldNamesExclude(Demo.class, "id");
        assertThat(fieldNames).contains("modify_time");
    }

    @Test
    void getCamelFieldNamesExcludeWhenNull() {
        List<String> fieldNames = TableWrapper.getCamelFieldNamesExclude(Demo.class);
        assertThat(fieldNames).contains("id", "modify_time");
    }

    @Data
    static class Parent {
        private Long id;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    static class Demo extends Parent {
        private Instant modifyTime;
    }
}
