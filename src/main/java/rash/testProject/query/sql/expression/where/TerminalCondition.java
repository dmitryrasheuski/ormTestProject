package rash.testProject.query.sql.expression.where;

import lombok.Getter;
import rash.testProject.query.sql.expression.WhereExpression;
import rash.testProject.query.Context;


import java.util.Formatter;

@Getter
public abstract class TerminalCondition implements WhereExpression {
    private String key;
    private String value;
    private String template;

    public TerminalCondition(String template, String key, String value) {
        this.key = key;
        this.value = value;
        this.template = template;
    }

    @Override
    public String getWhere() {

        return new Formatter()
                .format(template, key, value)
                .toString();
    }

    @Override
    public void interpret(Context context) {
        context.add(this, getWhere());
    }
}
