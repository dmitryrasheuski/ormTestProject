package rash.testProject.query.sql.getQuery;

import rash.testProject.query.Context;
import rash.testProject.query.Parameter;
import rash.testProject.query.sql.expression.FromExpression;
import rash.testProject.query.sql.expression.SelectExpression;
import rash.testProject.query.sql.expression.WhereExpression;
import rash.testProject.query.sql.expression.where.TerminalCondition;

import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetQueryContextImpl implements Context {
    private HashMap<Object, Object> map;
    {
        map = new HashMap<>();
    }

    @Override
    public void add(Object key, Object value) {
        map.put(key, value);
    }

    @Override
    public String getExecutableString() {

        String select = map.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof SelectExpression)
                .map(Map.Entry::getValue)
                .map(value -> (String)value)
                .findFirst()
                .orElse("*");
        String from = map.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof FromExpression)
                .map(Map.Entry::getValue)
                .map(value -> (String)value)
                .max((str1, str2) -> (str1.length() > str2.length()) ? 1 : -1)
                .orElseThrow(NullPointerException::new);
        String where = map.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof WhereExpression)
                .map(Map.Entry::getValue)
                .map(value -> (String)value)
                .max((str1, str2) -> (str1.length() > str2.length()) ? 1 : -1)
                .orElse(null);

        Formatter formatter = new Formatter().
                format("SELECT %s FROM %s", select, from);

        if ( where != null ) {
            formatter.format(" WHERE %s", where);
        }

        return formatter.toString();
    }

    @Override
    public List<Parameter> getParameters() {
        return map.keySet().stream()
                .filter(key -> key instanceof TerminalCondition)
                .map(key-> (TerminalCondition)key)
                .map(key -> new ParameterImpl(key.getKey(), key.getValue()))
                .collect(Collectors.toList());
    }
}
