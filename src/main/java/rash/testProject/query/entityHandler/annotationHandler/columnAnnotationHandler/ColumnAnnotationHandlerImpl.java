package rash.testProject.query.entityHandler.annotationHandler.columnAnnotationHandler;

import javax.persistence.Column;
import java.lang.reflect.AccessibleObject;

public class ColumnAnnotationHandlerImpl implements ColumnAnnotationHandler {
    @Override
    public String getColumnName(AccessibleObject property) {
        Column annotation = property.getAnnotation(Column.class);
        return annotation.name();
    }

    @Override
    public String getTableName(AccessibleObject property) {
        Column annotation = property.getAnnotation(Column.class);
        return annotation.table();
    }
}
