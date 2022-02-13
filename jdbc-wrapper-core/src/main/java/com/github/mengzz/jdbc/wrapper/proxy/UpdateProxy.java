package com.github.mengzz.jdbc.wrapper.proxy;

import com.github.mengzz.jdbc.wrapper.visitor.SqlVisitor;
import org.springframework.data.relational.core.sql.*;
import org.springframework.util.Assert;

import java.util.List;

/**
 * The type Update proxy.
 *
 * @author mengzz
 */
public class UpdateProxy extends CommonProxy implements Update {
    private final List<Assignment> assignments;

    public UpdateProxy(Update update) {
        super(update);
        SqlVisitor visitor = SqlVisitor.visit(update);
        table = visitor.getTable();
        assignments = visitor.getAssignments();
    }

    public static UpdateProxy of(Update update) {
        return new UpdateProxy(update);
    }

    @Override
    public void visit(Visitor visitor) {

        Assert.notNull(visitor, "Visitor must not be null!");

        visitor.enter(this);

        this.table.visit(visitor);
        this.assignments.forEach(it -> it.visit(visitor));

        visitIfNotNull(where, visitor);

        visitor.leave(this);
    }
}
