package rash.testProject.query.entityHandler.columnMetadataHandler;

import java.lang.reflect.AccessibleObject;

public interface ColumnMetadataHandler {
    String getColumnName(AccessibleObject property);
    String getTableName(AccessibleObject property);
}
