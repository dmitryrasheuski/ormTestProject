package rash.testProject.query.sql.expression.where.compaund;

import rash.testProject.query.sql.expression.WhereExpression;
import rash.testProject.query.sql.expression.where.CompoundCondition;

public class AndCondition extends CompoundCondition {
    private static final String TEMPLATE = " AND %s";

    public AndCondition(WhereExpression[] conditions) {
        super(TEMPLATE, conditions);
    }
}
