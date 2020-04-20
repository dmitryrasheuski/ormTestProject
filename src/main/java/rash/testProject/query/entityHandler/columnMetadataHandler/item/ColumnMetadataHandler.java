package rash.testProject.query.entityHandler.columnMetadataHandler.item;

import rash.testProject.query.entityHandler.annotationHandler.columnAnnotationHandler.ColumnAnnotationHandler;

import javax.persistence.Column;
import java.lang.reflect.AccessibleObject;

public class ColumnMetadataHandler extends AbstractColumnMetadataHandler {
    private ColumnAnnotationHandler annotationHandler;

    public ColumnMetadataHandler(ColumnAnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean isCorrect(AccessibleObject property) {
        return property.isAnnotationPresent(Column.class);
    }

    @Override
    protected String getColumnName(AccessibleObject property) {
        return annotationHandler.getColumnName(property);
    }

    @Override
    protected String getTableName(AccessibleObject property) {
        return annotationHandler.getTableName(property);
    }
}
