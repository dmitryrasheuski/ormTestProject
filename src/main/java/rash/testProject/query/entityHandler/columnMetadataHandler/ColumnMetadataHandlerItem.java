package rash.testProject.query.entityHandler.columnMetadataHandler;

import java.lang.reflect.AccessibleObject;

public interface ColumnMetadataHandlerItem {
    String findColumnName(AccessibleObject property);
    String findTableName(AccessibleObject property);
    void setNext(ColumnMetadataHandlerItem successor);
}
