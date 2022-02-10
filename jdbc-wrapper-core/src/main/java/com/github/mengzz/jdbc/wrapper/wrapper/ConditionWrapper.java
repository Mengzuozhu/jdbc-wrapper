package com.github.mengzz.jdbc.wrapper.wrapper;

import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.core.sql.Visitor;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * The type Condition wrapper.
 *
 * @author mengzz
 */
public class ConditionWrapper extends CommonWrapper implements Condition {
    public static final String PERCENT = "%";
    private Condition holder = null;
    private boolean ignoreNull = true;

    /**
     * Instantiates a new Condition wrapper.
     *
     * @param table the table
     */
    public ConditionWrapper(Table table) {
        super(table);
    }

    /**
     * Of.
     *
     * @param table the table
     * @return the condition wrapper
     */
    public static ConditionWrapper of(Table table) {
        return new ConditionWrapper(table);
    }

    /**
     * Of.
     *
     * @param table the table
     * @return the condition wrapper
     */
    public static ConditionWrapper of(String table) {
        return new ConditionWrapper(Table.create(table));
    }

    /**
     * Ignore null value.
     *
     * @param ignore the ignore
     * @return the condition wrapper
     */
    public ConditionWrapper ignoreNullValue(boolean ignore) {
        ignoreNull = ignore;
        return this;
    }

    /**
     * Enable camel column.
     *
     * @param enable the enable
     * @return the condition wrapper
     */
    @Override
    public ConditionWrapper enableCamelColumn(boolean enable) {
        super.enableCamelColumn(enable);
        return this;
    }

    /**
     * And eq.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andEq(String name, Object content) {
        return and(content, () -> column(name).isEqualTo(literalOf(content)));
    }

    /**
     * Or eq.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orEq(String name, Object content) {
        return or(content, () -> column(name).isEqualTo(literalOf(content)));
    }

    /**
     * And ne.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andNe(String name, Object content) {
        return and(content, () -> column(name).isNotEqualTo(literalOf(content)));
    }

    /**
     * Or ne.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orNe(String name, Object content) {
        return or(content, () -> column(name).isNotEqualTo(literalOf(content)));
    }

    /**
     * And lt.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andLt(String name, Object content) {
        return and(content, () -> column(name).isLess(literalOf(content)));
    }

    /**
     * Or lt.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orLt(String name, Object content) {
        return or(content, () -> column(name).isLess(literalOf(content)));
    }

    /**
     * And le.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andLe(String name, Object content) {
        return and(content, () -> column(name).isLessOrEqualTo(literalOf(content)));
    }

    /**
     * Or le.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orLe(String name, Object content) {
        return or(content, () -> column(name).isLessOrEqualTo(literalOf(content)));
    }

    /**
     * And gt.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andGt(String name, Object content) {
        return and(content, () -> column(name).isGreater(literalOf(content)));
    }

    /**
     * Or gt.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orGt(String name, Object content) {
        return or(content, () -> column(name).isGreater(literalOf(content)));
    }

    /**
     * And ge.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andGe(String name, Object content) {
        return and(content, () -> column(name).isGreaterOrEqualTo(literalOf(content)));
    }

    /**
     * Or ge.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orGe(String name, Object content) {
        return or(content, () -> column(name).isGreaterOrEqualTo(literalOf(content)));
    }

    /**
     * And like.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andLike(String name, String content) {
        return and(content, () -> column(name).like(literalOf(content)));
    }

    /**
     * Or like.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orLike(String name, String content) {
        return or(content, () -> column(name).like(literalOf(content)));
    }

    /**
     * And like.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper andContaining(String name, String content) {
        return and(content, () -> column(name).like(literalOf(getContainContent(content))));
    }

    /**
     * Or like.
     *
     * @param name    the name
     * @param content the content
     * @return the condition wrapper
     */
    public ConditionWrapper orContaining(String name, String content) {
        return or(content, () -> column(name).like(literalOf(getContainContent(content))));
    }

    /**
     * And in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper andIn(String name, Object... contents) {
        return and(contents, () -> column(name).in(literalOf(contents)));
    }

    /**
     * Or in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper orIn(String name, Object... contents) {
        return or(contents, () -> column(name).in(literalOf(contents)));
    }

    /**
     * And in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper andIn(String name, Collection<Object> contents) {
        return and(contents, () -> column(name).in(literalOf(contents)));
    }

    /**
     * Or in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper orIn(String name, Collection<Object> contents) {
        return or(contents, () -> column(name).in(literalOf(contents)));
    }

    /**
     * And not in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper andNotIn(String name, Object... contents) {
        return and(contents, () -> column(name).notIn(literalOf(contents)));
    }

    /**
     * Or not in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper orNotIn(String name, Object... contents) {
        return or(contents, () -> column(name).notIn(literalOf(contents)));
    }

    /**
     * And not in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper andNotIn(String name, Collection<Object> contents) {
        return and(contents, () -> column(name).notIn(literalOf(contents)));
    }

    /**
     * Or not in.
     *
     * @param name     the name
     * @param contents the contents
     * @return the condition wrapper
     */
    public ConditionWrapper orNotIn(String name, Collection<Object> contents) {
        return or(contents, () -> column(name).notIn(literalOf(contents)));
    }

    /**
     * And is null.
     *
     * @param name the name
     * @return the condition wrapper
     */
    public ConditionWrapper andIsNull(String name) {
        return and(column(name).isNull());
    }

    /**
     * Or is null.
     *
     * @param name the name
     * @return the condition wrapper
     */
    public ConditionWrapper orIsNull(String name) {
        return or(column(name).isNull());
    }

    /**
     * And is not null.
     *
     * @param name the name
     * @return the condition wrapper
     */
    public ConditionWrapper andIsNotNull(String name) {
        return and(column(name).isNotNull());
    }

    /**
     * Or is not null.
     *
     * @param name the name
     * @return the condition wrapper
     */
    public ConditionWrapper orIsNotNull(String name) {
        return or(column(name).isNotNull());
    }

    /**
     * And nest.
     *
     * @param condition the condition
     * @return the condition wrapper
     */
    public ConditionWrapper andNest(Condition condition) {
        return and(condition, () -> Conditions.nest(condition));
    }

    /**
     * Or nest.
     *
     * @param condition the condition
     * @return the condition wrapper
     */
    public ConditionWrapper orNest(Condition condition) {
        return or(condition, () -> Conditions.nest(condition));
    }

    /**
     * And.
     *
     * @param other the other
     * @return the condition wrapper
     */
    @Override
    public ConditionWrapper and(Condition other) {
        if (holder == null) {
            holder = other;
        } else {
            holder = holder.and(other);
        }
        return this;
    }

    /**
     * Or.
     *
     * @param other the other
     * @return the condition wrapper
     */
    @Override
    public ConditionWrapper or(Condition other) {
        if (holder == null) {
            holder = other;
        } else {
            holder = holder.or(other);
        }
        return this;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        if (holder == null) {
            return "1 = 1";
        }
        return holder.toString();
    }

    /**
     * Visit.
     *
     * @param visitor the visitor
     */
    @Override
    public void visit(Visitor visitor) {
        if (holder != null) {
            holder.visit(visitor);
        }
    }

    private String getContainContent(String content) {
        return PERCENT + content + PERCENT;
    }

    /**
     * And.
     *
     * @param content  the content
     * @param supplier the supplier
     * @return the condition wrapper
     */
    private ConditionWrapper and(Object content, Supplier<Condition> supplier) {
        if (ignoreNull && content == null) {
            return this;
        }

        Condition condition = supplier.get();
        return and(condition);
    }

    /**
     * Or.
     *
     * @param content  the content
     * @param supplier the supplier
     * @return the condition wrapper
     */
    private ConditionWrapper or(Object content, Supplier<Condition> supplier) {
        if (ignoreNull && content == null) {
            return this;
        }

        Condition condition = supplier.get();
        return or(condition);
    }


}
