package rash.testProject.query.sql.expression.where.compaund;

import rash.testProject.query.sql.expression.WhereExpression;
import rash.testProject.query.sql.expression.where.CompoundCondition;

public class OrCondition extends CompoundCondition {
    private static final String TEMPLATE = " OR %s";

    public OrCondition(WhereExpression... conditions) {
        super(TEMPLATE, conditions);
    }
}
