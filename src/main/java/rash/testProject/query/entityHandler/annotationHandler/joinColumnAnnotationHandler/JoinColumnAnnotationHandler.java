package rash.testProject.query.entityHandler.annotationHandler.joinColumnAnnotationHandler;

import java.lang.reflect.AccessibleObject;

public interface JoinColumnAnnotationHandler {
    String getColumnName(AccessibleObject property);
    String getTableName(AccessibleObject property);
    String getReferencedColumnName(AccessibleObject property);
}
