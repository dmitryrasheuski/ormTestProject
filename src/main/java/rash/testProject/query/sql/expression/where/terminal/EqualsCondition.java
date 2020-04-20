package rash.testProject.query.sql.expression.where.terminal;

import rash.testProject.query.sql.expression.where.TerminalCondition;

public class EqualsCondition extends TerminalCondition {
    private static final String TEMPLATE = "%s = '%s'";

    public EqualsCondition(String key, String value) {
        super(TEMPLATE, key, value);
    }

}
