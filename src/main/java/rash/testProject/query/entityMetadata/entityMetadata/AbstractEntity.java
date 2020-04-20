package rash.testProject.query.entityMetadata.entityMetadata;

import rash.testProject.query.entityMetadata.EntityColumn;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.entityMetadata.JoinCondition;

import java.util.List;
import java.util.Map;

public abstract class AbstractEntity implements EntityMetadata{

    protected Class<?> entityClass;
    protected String mainTableName;
    protected Map<String, JoinCondition> secondaryTablesMap;
    protected List<EntityColumn> columns;
    protected List<EntityMetadata> secondaryEntities;

    protected AbstractEntity(Class<?> entityClass,
                             String mainTableName,
                             Map<String, JoinCondition> secondaryTablesMap,
                             List<EntityColumn> columns,
                             List<EntityMetadata> secondaryEntities) {
        this.entityClass = entityClass;
        this.mainTableName = mainTableName;
        this.secondaryTablesMap = secondaryTablesMap;
        this.columns = columns;
        this.secondaryEntities = secondaryEntities;
    }

    @Override
    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public String getMainTableName() {
        return mainTableName;
    }

    @Override
    public Map<String, JoinCondition> getSecondaryTablesMap() {
        return secondaryTablesMap;
    }

    @Override
    public List<EntityColumn> getColumns() {
        return columns;
    }

    @Override
    public List<EntityMetadata> getSecondaryEntities() {
        return secondaryEntities;
    }

    //@Override
//    public String getFrom(){
//        Formatter formatter = new Formatter().format(mainTableName);
//
//        HashMap<String, JoinCondition> allSecondaryTable = secondaryEntities.stream()
//                .map(AbstractEntity::getSecondaryTablesMap)
//                .collect(HashMap<String, JoinCondition>::new, HashMap<String, JoinCondition>::putAll, HashMap<String, JoinCondition>::putAll);
//        allSecondaryTable.putAll(secondaryTablesMap);
//
//        allSecondaryTable.forEach( (secondaryTableName, joinCondition) ->
//                formatter.format(" JOIN %s ON %s.%s = %s.%s", secondaryTableName, joinCondition.getMainTableName(), joinCondition.getMainTableColumnName(), joinCondition.getSecondaryTableName(), joinCondition.getSecondaryTableColumnName())
//        );
//
//        secondaryEntities.forEach(joinTable ->
//                formatter.format(" JOIN %s", joinTable.getFrom())
//        );
//
//        return formatter.toString();
//    }
//
//    //@Override
//    public void interpret(Context context) {
//        context.add(this, getFrom());
//        for (AbstractEntity secondaryEntity : secondaryEntities) {
//            secondaryEntity.interpret(context);
//        }
//
//    }
}
