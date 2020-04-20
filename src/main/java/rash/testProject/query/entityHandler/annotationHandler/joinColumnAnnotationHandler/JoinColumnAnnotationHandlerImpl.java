package rash.testProject.query.entityHandler.annotationHandler.joinColumnAnnotationHandler;

import javax.persistence.JoinColumn;
import java.lang.reflect.AccessibleObject;

public class JoinColumnAnnotationHandlerImpl implements JoinColumnAnnotationHandler {
    @Override
    public String getColumnName(AccessibleObject property) {
        JoinColumn annotation = property.getAnnotation(JoinColumn.class);
        return annotation.name();
    }

    @Override
    public String getTableName(AccessibleObject property) {
        JoinColumn annotation = property.getAnnotation(JoinColumn.class);
        return annotation.table();
    }

    @Override
    public String getReferencedColumnName(AccessibleObject property) {
        JoinColumn annotation = property.getAnnotation(JoinColumn.class);
        return annotation.referencedColumnName();
    }
}
