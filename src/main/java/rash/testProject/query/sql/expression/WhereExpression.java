package rash.testProject.query.sql.expression;

import rash.testProject.query.Context;
import rash.testProject.query.Expression;

public interface WhereExpression extends Expression {
    String getWhere();
}
