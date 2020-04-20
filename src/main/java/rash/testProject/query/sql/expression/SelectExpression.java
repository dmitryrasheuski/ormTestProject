package rash.testProject.query.sql.expression;

import rash.testProject.query.Context;
import rash.testProject.query.Expression;

public interface SelectExpression extends Expression {
    String getSelect();
}
