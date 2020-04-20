package rash.testProject.query.entityHandler.joinMetadataHandler.item;

import rash.testProject.query.entityHandler.annotationHandler.joinTableAnnotationHandler.JoinTableAnnotationHandler;

import javax.persistence.JoinTable;
import java.lang.reflect.AccessibleObject;

public class JoinTableMetadataHandlerItem extends AbstractJoinMetadataHandlerItem {
    private JoinTableAnnotationHandler annotationHandler;

    public JoinTableMetadataHandlerItem(JoinTableAnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean isCorrect(AccessibleObject property) {
        return property.isAnnotationPresent(JoinTable.class);
    }

    @Override
    protected String getMainEntityTableName(AccessibleObject property) {
        return annotationHandler.getThirdTableName(property);
    }

    @Override
    protected String getMainEntityTableColumnName(AccessibleObject property) {
        return annotationHandler.getInverseColumnName(property).get(0);
    }

    @Override
    protected String getSecondaryEntityTableName(AccessibleObject property) {
        return annotationHandler.getJoinEntityTableName(property).get(0);
    }

    @Override
    protected String getSecondaryEntityTableColumnName(AccessibleObject property) {
        return annotationHandler.getJoinEntityTableColumnName(property).get(0);
    }
}
