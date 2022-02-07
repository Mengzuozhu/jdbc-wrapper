package com.github.mengzz.jdbc.wrapper.wrapper;

import com.github.mengzz.jdbc.wrapper.util.CamelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author mengzz
 **/
public class TableWrapper {
    private static Map<Class<?>, List<String>> typeAndFieldNames = new ConcurrentReferenceHashMap<>();
    private static NamingStrategy namingStrategy = NamingStrategy.INSTANCE;

    /**
     * Gets table name.
     *
     * @param persistentEntity the persistent entity
     * @return the table name
     */
    public static String getTableName(RelationalPersistentEntity<?> persistentEntity) {
        Table annotation = persistentEntity.findAnnotation(Table.class);
        return Optional.ofNullable(annotation)
                .map(Table::value)
                .filter(StringUtils::isNotEmpty)
                .orElseGet(() -> namingStrategy.getTableName(persistentEntity.getType()));
    }

    /**
     * Gets all camel field names.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the all field names
     */
    public static <T> List<String> getAllCamelFieldNames(Class<T> type) {
        List<String> list = typeAndFieldNames.get(type);
        if (list != null) {
            return list;
        }

        Field[] fields = FieldUtils.getAllFields(type);
        list = new ArrayList<>();
        for (Field field : fields) {
            list.add(CamelUtil.toUnderscore(field.getName()));
        }
        typeAndFieldNames.put(type, list);
        return list;
    }

    /**
     * Gets camel field names exclude.
     *
     * @param <T>      the type parameter
     * @param type     the type
     * @param excludes the excludes
     * @return the camel field names exclude
     */
    public static <T> List<String> getCamelFieldNamesExclude(Class<T> type, Collection<String> excludes) {
        List<String> allCamelFieldNames = getAllCamelFieldNames(type);
        List<String> res = new ArrayList<>(allCamelFieldNames);
        res.removeIf(excludes::contains);
        return res;
    }

    /**
     * Gets camel field names exclude.
     *
     * @param <T>      the type parameter
     * @param type     the type
     * @param excludes the excludes
     * @return the camel field names exclude
     */
    public static <T> List<String> getCamelFieldNamesExclude(Class<T> type, String... excludes) {
        if (excludes == null) {
            return getAllCamelFieldNames(type);
        }
        return getCamelFieldNamesExclude(type, Arrays.asList(excludes));
    }
}
