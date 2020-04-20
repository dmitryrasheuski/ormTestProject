package rash.testProject.query.entityHandler.joinMetadataHandler;

import java.lang.reflect.AccessibleObject;

public interface JoinMetadataHandler {
    String getMainEntityTableName(AccessibleObject properties);
    String getMainEntityTableColumnName(AccessibleObject properties);
    String getSecondaryEntityTableName(AccessibleObject properties);
    String getSecondaryEntityTableColumnName(AccessibleObject properties);
}
