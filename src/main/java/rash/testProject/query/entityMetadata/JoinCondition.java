package rash.testProject.query.entityMetadata;

public interface JoinCondition {
    String getMainTableName();
    String getMainTableColumnName();
    String getSecondaryTableName();
    String getSecondaryTableColumnName();
}
