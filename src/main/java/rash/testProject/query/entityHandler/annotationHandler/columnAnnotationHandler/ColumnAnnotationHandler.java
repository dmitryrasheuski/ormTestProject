package rash.testProject.query.entityHandler.annotationHandler.columnAnnotationHandler;

import java.lang.reflect.AccessibleObject;

public interface ColumnAnnotationHandler {
    String getColumnName(AccessibleObject property);
    String getTableName(AccessibleObject property);
}
