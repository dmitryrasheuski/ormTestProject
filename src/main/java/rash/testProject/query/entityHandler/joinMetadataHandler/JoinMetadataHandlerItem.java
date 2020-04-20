package rash.testProject.query.entityHandler.joinMetadataHandler;

import java.lang.reflect.AccessibleObject;

public interface JoinMetadataHandlerItem {
    void setNext(JoinMetadataHandlerItem annotationHandler);
    String findMainEntityTableName(AccessibleObject properties);
    String findMainEntityTableColumnName(AccessibleObject properties);
    String findSecondaryEntityTableName(AccessibleObject properties);
    String findSecondaryEntityTableColumnName(AccessibleObject properties);
}
