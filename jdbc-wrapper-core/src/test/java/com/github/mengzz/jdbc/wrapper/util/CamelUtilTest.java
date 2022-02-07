package com.github.mengzz.jdbc.wrapper.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
class CamelUtilTest {

    @Test
    void toUnderscore() {
        assertEquals("test_name", CamelUtil.toUnderscore("testName"));
        assertEquals("test_name", CamelUtil.toUnderscore("test_name"));
        assertEquals("test_name1", CamelUtil.toUnderscore("testName1"));
        assertEquals("test_n_name", CamelUtil.toUnderscore("testNName"));
        assertEquals("", CamelUtil.toUnderscore(""));
    }

    @Test
    void toUnderscoreList() {
        String[] strings = {"testName", "testName1"};
        List<String> res = new ArrayList<>(Arrays.asList("test_name", "test_name1"));
        assertEquals(res, CamelUtil.toUnderscore(strings));
        assertEquals(res, CamelUtil.toUnderscore(new ArrayList<>(Arrays.asList(strings))));
        assertEquals(new ArrayList<>(), CamelUtil.toUnderscore((String[]) null));
    }

    @Test
    void toUnderscoreShouldNull() {
        assertNull(CamelUtil.toUnderscore((String) null));
    }
}
