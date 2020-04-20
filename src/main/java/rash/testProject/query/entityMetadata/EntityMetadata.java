package rash.testProject.query.entityMetadata;

import java.util.List;
import java.util.Map;

public interface EntityMetadata {
    String getMainTableName();
    Class<?> getEntityClass();
    Map<String, JoinCondition> getSecondaryTablesMap();
    List<EntityMetadata> getSecondaryEntities();
    List<EntityColumn> getColumns();
}
