package rash.testProject.query.entityMetadata.entityMetadata;

import rash.testProject.query.entityMetadata.EntityColumn;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.entityMetadata.JoinCondition;

import java.util.List;
import java.util.Map;

public class MainEntity extends AbstractEntity {
    public MainEntity(Class<?> entityClass, String mainTableName, Map<String, JoinCondition> secondaryTablesMap, List<EntityColumn> columns, List<EntityMetadata> secondaryEntities) {
        super(entityClass, mainTableName, secondaryTablesMap, columns, secondaryEntities);
    }
}
