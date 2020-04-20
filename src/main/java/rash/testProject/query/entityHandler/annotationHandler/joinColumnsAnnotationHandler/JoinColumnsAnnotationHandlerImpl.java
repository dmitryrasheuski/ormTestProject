package rash.testProject.query.entityHandler.annotationHandler.joinColumnsAnnotationHandler;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JoinColumnsAnnotationHandlerImpl implements JoinColumnsAnnotationHandler {
    @Override
    public List<String> getColumnNames(AccessibleObject property) {
        JoinColumns annotation = property.getAnnotation(JoinColumns.class);
        JoinColumn[] joinColumns = annotation.value();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTableNames(AccessibleObject property) {
        JoinColumns annotation = property.getAnnotation(JoinColumns.class);
        JoinColumn[] joinColumns = annotation.value();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::table)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getReferencedColumnNames(AccessibleObject property) {
        JoinColumns annotation = property.getAnnotation(JoinColumns.class);
        JoinColumn[] joinColumns = annotation.value();

        return Arrays.stream(joinColumns)
                .map(JoinColumn::referencedColumnName)
                .collect(Collectors.toList());
    }
}
