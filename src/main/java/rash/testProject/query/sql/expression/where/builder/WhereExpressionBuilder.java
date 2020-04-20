package rash.testProject.query.sql.expression.where.builder;

import rash.testProject.query.sql.expression.WhereExpression;
import rash.testProject.query.sql.expression.where.compaund.AndCondition;
import rash.testProject.query.sql.expression.where.compaund.OrCondition;

import java.util.*;

public class WhereExpressionBuilder {
    private static final String NONE_PREFIX = "NONE";
    private static final String AND_PREFIX = "AND";
    private static final String OR_PREFIX = "OR";

    private WhereExpression condition;
    private List<WhereExpression> conditions;
    private List<String> prefixes;
    
    public WhereExpressionBuilder() {
        conditions = new ArrayList<>();
        prefixes = new ArrayList<>();
    }

    public WhereExpressionBuilder condition(WhereExpression condition) {
        conditions.add(condition);
        prefixes.add(NONE_PREFIX);
        return this;
    }

    public WhereExpressionBuilder and(WhereExpression condition) {
        conditions.add(condition);
        prefixes.add(AND_PREFIX);
        return this;
    }
    
    public WhereExpressionBuilder or(WhereExpression condition) {
        conditions.add(condition);
        prefixes.add(OR_PREFIX);
        return this;
    }
    
    public WhereExpression build() {
        return getCondition(conditions.size() - 1);
    }

    protected WhereExpression getCondition(int lastIndex) {

        for (int i = lastIndex; i > 0; i--) {
            String currentPrefix = prefixes.get(i);
            if (currentPrefix == OR_PREFIX) {
                WhereExpression prevCondition = getCondition(i - 1);
                WhereExpression currentCondition = conditions.get(i);
                return new OrCondition(prevCondition, currentCondition);
            }
        }

        List<WhereExpression> conditionList = conditions.subList(0, lastIndex + 1);
        WhereExpression[] conditionArr = conditionList.toArray( new WhereExpression[conditionList.size()] );
        return new AndCondition(conditionArr);

    }
    
}
