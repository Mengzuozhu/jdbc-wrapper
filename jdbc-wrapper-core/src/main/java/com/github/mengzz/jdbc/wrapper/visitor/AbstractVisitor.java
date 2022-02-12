package com.github.mengzz.jdbc.wrapper.visitor;

import org.springframework.core.ResolvableType;
import org.springframework.data.relational.core.sql.Visitable;
import org.springframework.data.relational.core.sql.Visitor;

/**
 * The type Abstract visitor.
 *
 * @param <T> the type parameter
 * @author mengzz
 */
public abstract class AbstractVisitor<T> implements Visitor {
    private final ResolvableType type;

    public AbstractVisitor() {
        this.type = ResolvableType.forClass(getClass()).as(AbstractVisitor.class).getGeneric(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void enter(Visitable segment) {
        if (type.isInstance(segment)) {
            intercept((T) segment);
        }
    }

    /**
     * Intercept.
     *
     * @param segment the segment
     */
    public abstract void intercept(T segment);

}
