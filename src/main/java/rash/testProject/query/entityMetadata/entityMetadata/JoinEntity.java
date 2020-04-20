package rash.testProject.query.entityMetadata.entityMetadata;

import rash.testProject.query.entityMetadata.EntityColumn;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.entityMetadata.JoinCondition;

import java.util.List;
import java.util.Map;

public class JoinEntity extends AbstractEntity {
    protected JoinCondition joinCondition;

    public JoinEntity(Class<?> entityClass, String mainTableName, Map<String, JoinCondition> secondaryTablesMap, List<EntityColumn> columns, List<EntityMetadata> secondaryEntities, JoinCondition joinCondition) {
        super(entityClass, mainTableName, secondaryTablesMap, columns, secondaryEntities);
        this.joinCondition = joinCondition;
    }

    public JoinCondition getJoinCondition() {
        return joinCondition;
    }
}
