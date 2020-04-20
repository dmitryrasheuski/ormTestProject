package rash.testProject.query.sql.expression.where.terminal;

import rash.testProject.query.sql.expression.where.TerminalCondition;

public class LikeCondition extends TerminalCondition {
    private static final String TEMPLATE = "%s LIKE '%s'";

    public LikeCondition(String key, String value) {
        super(TEMPLATE, key, value);
    }
}
