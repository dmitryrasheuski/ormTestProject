package rash.testProject.query.sql.expression.where;

import rash.testProject.query.sql.expression.WhereExpression;
import rash.testProject.query.Context;

import java.util.Formatter;

public abstract class CompoundCondition implements WhereExpression {
    private WhereExpression[] conditions;
    private String template;

    public CompoundCondition(String template, WhereExpression[] conditions) {
        this.conditions = conditions;
        this.template = template;
    }

    @Override
    public String getWhere() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "( " );
        stringBuilder.append( conditions[0].getWhere() );
        Formatter formatter = new Formatter();
        for (int i = 1; i < conditions.length; i++) {
            formatter.format(template, conditions[i].getWhere());
        }
        stringBuilder.append( formatter.toString() );
        stringBuilder.append( " )" );

        return stringBuilder.toString();
    }

    @Override
    public void interpret(Context context) {
        context.add(this, getWhere());
        for (WhereExpression condition : conditions) {
            condition.interpret(context);
        }

    }
}
