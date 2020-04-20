package rash.testProject.query.entityHandler.annotationHandler.joinTableAnnotationHandler;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JoinTableAnnotationHandlerImpl implements JoinTableAnnotationHandler {
    @Override
    public String getThirdTableName(AccessibleObject property) {
        JoinTable annotation = property.getAnnotation(JoinTable.class);
        return annotation.name();
    }

    @Override
    public List<String> getMainEntityTableName(AccessibleObject property) {
        JoinTable annotation = property.getAnnotation(JoinTable.class);
        JoinColumn[] joinColumns = annotation.joinColumns();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::table)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getJoinEntityTableName(AccessibleObject property) {
        JoinTable annotation = property.getAnnotation(JoinTable.class);
        JoinColumn[] joinColumns = annotation.inverseJoinColumns();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::table)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getReferenceColumnName(AccessibleObject properties) {
        JoinTable annotation = properties.getAnnotation(JoinTable.class);
        JoinColumn[] joinColumns = annotation.joinColumns();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getMainEntityTableColumnName(AccessibleObject properties) {
        JoinTable annotation = properties.getAnnotation(JoinTable.class);
        JoinColumn[] joinColumns = annotation.joinColumns();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::referencedColumnName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getInverseColumnName(AccessibleObject properties) {
        JoinTable annotation = properties.getAnnotation(JoinTable.class);
        JoinColumn[] joinColumns = annotation.inverseJoinColumns();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getJoinEntityTableColumnName(AccessibleObject properties) {
        JoinTable annotation = properties.getAnnotation(JoinTable.class);
        JoinColumn[] joinColumns = annotation.inverseJoinColumns();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::referencedColumnName)
                .collect(Collectors.toList());
    }
}
