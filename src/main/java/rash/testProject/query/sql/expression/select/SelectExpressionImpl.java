package rash.testProject.query.sql.expression.select;

import lombok.AllArgsConstructor;
import rash.testProject.query.sql.expression.SelectExpression;
import rash.testProject.query.entityMetadata.EntityColumn;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.Context;

import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SelectExpressionImpl implements SelectExpression {
    private EntityMetadata entityMetadata;

    @Override
    public String getSelect() {
        Formatter formatter = new Formatter();

        List<String> fullColumnNames = entityMetadata.getColumns().stream()
                .map(EntityColumn::getFullName)
                .collect(Collectors.toList());
        List<String> joinEntityFullColumnNames = entityMetadata.getSecondaryEntities().stream()
                .map(EntityMetadata::getColumns)
                .flatMap(List::stream)
                .map(EntityColumn::getFullName)
                .collect(Collectors.toList());
        fullColumnNames.addAll(joinEntityFullColumnNames);

        fullColumnNames.forEach(columnName -> formatter.format("%s, ", columnName));

        String string = formatter.toString();
        return string.replaceFirst("(?<=.), $", "");
    }

    @Override
    public void interpret(Context context) {
        context.add(this, getSelect());
    }
}
