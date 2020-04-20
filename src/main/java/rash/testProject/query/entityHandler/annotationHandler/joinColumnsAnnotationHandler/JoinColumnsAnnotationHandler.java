package rash.testProject.query.entityHandler.annotationHandler.joinColumnsAnnotationHandler;

import java.lang.reflect.AccessibleObject;
import java.util.List;

public interface JoinColumnsAnnotationHandler {
    List<String> getColumnNames(AccessibleObject property);
    List<String> getTableNames(AccessibleObject property);
    List<String> getReferencedColumnNames(AccessibleObject property);
}
