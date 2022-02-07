package com.github.mengzz.jdbc.wrapper.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mengzz
 **/
class TypeUtilTest {

    @Test
    void doIfClass() {
        Object obj = "test";
        TypeUtil.doIfClass(obj, String.class, Assertions::assertNotNull);
        TypeUtil.doIfClass(obj, Number.class, Assertions::assertNull);
    }

    @Test
    void tryConvert() {
        Object obj = "test";
        String res = TypeUtil.tryConvert(obj, String.class);
        assertThat(res).isEqualTo(obj);
        assertThat(TypeUtil.tryConvert(obj, Number.class)).isNull();
    }
}
