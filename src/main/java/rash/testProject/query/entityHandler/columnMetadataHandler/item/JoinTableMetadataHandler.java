package rash.testProject.query.entityHandler.columnMetadataHandler.item;

import rash.testProject.query.entityHandler.annotationHandler.joinTableAnnotationHandler.JoinTableAnnotationHandler;

import javax.persistence.JoinTable;
import java.lang.reflect.AccessibleObject;

public class JoinTableMetadataHandler extends AbstractColumnMetadataHandler {
    private JoinTableAnnotationHandler annotationHandler;

    public JoinTableMetadataHandler(JoinTableAnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean isCorrect(AccessibleObject property) {
        return property.isAnnotationPresent(JoinTable.class);
    }

    @Override
    protected String getColumnName(AccessibleObject property) {
        return annotationHandler.getInverseColumnName(property).get(0);
    }

    @Override
    protected String getTableName(AccessibleObject property) {
        return annotationHandler.getThirdTableName(property);
    }
}
