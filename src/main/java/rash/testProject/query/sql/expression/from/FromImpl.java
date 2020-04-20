package rash.testProject.query.sql.expression.from;

import lombok.AllArgsConstructor;
import rash.testProject.query.Context;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.entityMetadata.JoinCondition;
import rash.testProject.query.entityMetadata.entityMetadata.JoinEntity;
import rash.testProject.query.sql.expression.FromExpression;

import java.util.Formatter;
import java.util.HashMap;

@AllArgsConstructor
public class FromImpl implements FromExpression {
    private EntityMetadata entityMetadata;

    @Override
    public String getFrom() {
        Formatter formatter = new Formatter()
                .format( entityMetadata.getMainTableName() );

        HashMap<String, JoinCondition> allSecondaryTable = entityMetadata.getSecondaryEntities().stream()
                .map(EntityMetadata::getSecondaryTablesMap)
                .collect(
                        HashMap<String, JoinCondition>::new,
                        HashMap<String, JoinCondition>::putAll,
                        HashMap<String, JoinCondition>::putAll
                );
        allSecondaryTable.putAll( entityMetadata.getSecondaryTablesMap() );

        allSecondaryTable.forEach( (secondaryTableName, joinCondition) ->
                formatter.format(" JOIN %s ON %s.%s = %s.%s",
                        secondaryTableName,
                        joinCondition.getMainTableName(),
                        joinCondition.getMainTableColumnName(),
                        joinCondition.getSecondaryTableName(),
                        joinCondition.getSecondaryTableColumnName())
        );

        entityMetadata.getSecondaryEntities().forEach(joinEntity ->
                formatter.format(" JOIN %s", getFromForJoinEntity(joinEntity))
        );

        return formatter.toString();

    }

    protected String getFromForJoinEntity(EntityMetadata secondaryEntityMetadata) {
        JoinEntity joinEntityMetadata = (JoinEntity) secondaryEntityMetadata;

        Formatter formatter = new Formatter()
                .format("%s ON %s.%s = %s.%s",
                        joinEntityMetadata.getMainTableName(),
                        joinEntityMetadata.getJoinCondition().getMainTableName(),
                        joinEntityMetadata.getJoinCondition().getMainTableColumnName(),
                        joinEntityMetadata.getJoinCondition().getSecondaryTableName(),
                        joinEntityMetadata.getJoinCondition().getSecondaryTableColumnName()
                );

        joinEntityMetadata.getSecondaryEntities().forEach(joinEntity ->
                formatter.format(" JOIN %s", getFromForJoinEntity(joinEntity))
        );

        return formatter.toString();
    }

    @Override
    public void interpret(Context context) {
        context.add(this, getFrom());
    }
}
