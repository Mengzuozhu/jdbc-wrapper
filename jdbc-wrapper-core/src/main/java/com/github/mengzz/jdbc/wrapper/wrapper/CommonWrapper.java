package com.github.mengzz.jdbc.wrapper.wrapper;

import com.github.mengzz.jdbc.wrapper.util.CamelUtil;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Literal;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.data.relational.core.sql.Table;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The type Common wrapper.
 *
 * @author mengzz
 */
public class CommonWrapper {
    protected final Table table;

    private boolean enableCamelColumn = true;

    public CommonWrapper(Table table) {
        this.table = table;
    }

    public static CommonWrapper of(Table table) {
        return new CommonWrapper(table);
    }

    /**
     * Literal of.
     *
     * @param content the content
     * @return the literal
     */
    public static Literal<?> literalOf(Object content) {
        if (content instanceof CharSequence) {
            return SQL.literalOf((CharSequence) content);
        }
        return SQL.literalOf(content);
    }

    /**
     * Literal of.
     *
     * @param contents the contents
     * @return the literal [ ]
     */
    public static Literal<?>[] literalOf(Object... contents) {
        if (contents == null) {
            return new Literal<?>[0];
        }

        return literalOf(Arrays.asList(contents));
    }

    /**
     * Literal of.
     *
     * @param contents the contents
     * @return the literal [ ]
     */
    public static Literal<?>[] literalOf(Collection<Object> contents) {
        if (contents == null) {
            return new Literal<?>[0];
        }

        return contents.stream().map(CommonWrapper::literalOf).toArray(Literal[]::new);
    }

    /**
     * Enable camel column.
     *
     * @param enable the enable
     * @return the condition wrapper
     */
    public CommonWrapper enableCamelColumn(boolean enable) {
        enableCamelColumn = enable;
        return this;
    }

    /**
     * Column.
     *
     * @param name the name
     * @return the column
     */
    public Column column(String name) {
        if (enableCamelColumn) {
            name = CamelUtil.toUnderscore(name);
        }
        return table.column(name);
    }

    /**
     * Columns.
     *
     * @param names the names
     * @return the list
     */
    public List<Column> columns(String... names) {
        return columns(Arrays.asList(names));
    }

    /**
     * Columns.
     *
     * @param names the names
     * @return the list
     */
    public List<Column> columns(Collection<String> names) {
        if (enableCamelColumn) {
            names = CamelUtil.toUnderscore(names);
        }
        return table.columns(names);
    }

}
