package rash.testProject.query.entityHandler.columnMetadataHandler.item;

import rash.testProject.query.entityHandler.annotationHandler.joinColumnAnnotationHandler.JoinColumnAnnotationHandler;

import javax.persistence.JoinColumn;
import java.lang.reflect.AccessibleObject;

public class JoinColumnMetadataHandler extends AbstractColumnMetadataHandler {
    private JoinColumnAnnotationHandler annotationHandler;

    public JoinColumnMetadataHandler(JoinColumnAnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean isCorrect(AccessibleObject property) {
        return property.isAnnotationPresent(JoinColumn.class);
    }

    @Override
    protected String getColumnName(AccessibleObject property) {
        return annotationHandler.getColumnName(property);
    }

    @Override
    protected String getTableName(AccessibleObject property) {
        return null;
    }
}
