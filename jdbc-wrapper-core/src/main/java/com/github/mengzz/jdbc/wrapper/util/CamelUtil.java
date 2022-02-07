package com.github.mengzz.jdbc.wrapper.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.ParsingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author mengzz
 **/
public class CamelUtil {

    public static final String UNDERLINE = "_";

    /**
     * Camel to underscore.
     *
     * @param camel the camel
     * @return the string
     */
    public static String toUnderscore(String camel) {
        if (StringUtils.isBlank(camel)) {
            return camel;
        }
        return ParsingUtils.reconcatenateCamelCase(camel, UNDERLINE);
    }

    /**
     * Camel to underscore.
     *
     * @param camels the camels
     * @return the list
     */
    public static List<String> toUnderscore(String... camels) {
        if (camels == null) {
            return new ArrayList<>();
        }
        return toUnderscore(Arrays.asList(camels));
    }

    /**
     * Camel to underscore.
     *
     * @param camels the camels
     * @return the list
     */
    public static List<String> toUnderscore(Collection<String> camels) {
        List<String> underscores = new ArrayList<>();
        if (camels == null) {
            return underscores;
        }

        for (String name : camels) {
            underscores.add(toUnderscore(name));
        }
        return underscores;
    }
}
