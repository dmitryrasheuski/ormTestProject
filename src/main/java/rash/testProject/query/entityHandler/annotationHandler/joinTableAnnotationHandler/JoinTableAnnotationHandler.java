package rash.testProject.query.entityHandler.annotationHandler.joinTableAnnotationHandler;

import java.lang.reflect.AccessibleObject;
import java.util.List;

public interface JoinTableAnnotationHandler {
    String getThirdTableName(AccessibleObject property);
    List<String> getMainEntityTableName(AccessibleObject property);
    List<String> getJoinEntityTableName(AccessibleObject property);

    List<String> getReferenceColumnName(AccessibleObject properties);
    List<String> getMainEntityTableColumnName(AccessibleObject properties);
    List<String> getInverseColumnName(AccessibleObject properties);
    List<String> getJoinEntityTableColumnName(AccessibleObject properties);

}
