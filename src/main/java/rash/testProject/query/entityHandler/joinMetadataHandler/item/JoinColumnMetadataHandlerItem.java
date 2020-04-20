package rash.testProject.query.entityHandler.joinMetadataHandler.item;

import rash.testProject.query.entityHandler.annotationHandler.joinColumnAnnotationHandler.JoinColumnAnnotationHandler;

import javax.persistence.JoinColumn;
import java.lang.reflect.AccessibleObject;

public class JoinColumnMetadataHandlerItem extends AbstractJoinMetadataHandlerItem {
    private JoinColumnAnnotationHandler annotationHandler;

    public JoinColumnMetadataHandlerItem(JoinColumnAnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean isCorrect(AccessibleObject property) {
        return property.isAnnotationPresent(JoinColumn.class);
    }

    @Override
    protected String getMainEntityTableName(AccessibleObject property) {
        return null;
    }

    @Override
    protected String getMainEntityTableColumnName(AccessibleObject property) {
        return annotationHandler.getColumnName(property);
    }

    @Override
    protected String getSecondaryEntityTableName(AccessibleObject property) {
        return annotationHandler.getTableName(property);
    }

    @Override
    protected String getSecondaryEntityTableColumnName(AccessibleObject property) {
        return annotationHandler.getReferencedColumnName(property);
    }
}
