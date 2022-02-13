package com.github.mengzz.jdbc.wrapper.proxy;

import com.github.mengzz.jdbc.wrapper.visitor.ConditionVisitor;
import com.github.mengzz.jdbc.wrapper.visitor.SelectVisitor;
import org.springframework.data.relational.core.sql.Delete;
import org.springframework.data.relational.core.sql.Visitor;
import org.springframework.util.Assert;

/**
 * @author mengzz
 **/
public class DeleteProxy extends CommonProxy implements Delete {

    public DeleteProxy(Delete delete) {
        SelectVisitor visitor = SelectVisitor.visit(delete);
        where = visitor.getWhere();
        from = visitor.getFrom();
        condition = ConditionVisitor.visit(where).getCondition();
    }

    public static DeleteProxy of(Delete delete) {
        return new DeleteProxy(delete);
    }

    @Override
    public void visit(Visitor visitor) {

        Assert.notNull(visitor, "Visitor must not be null!");

        visitor.enter(this);

        from.visit(visitor);
        visitIfNotNull(where, visitor);

        visitor.leave(this);
    }
}
