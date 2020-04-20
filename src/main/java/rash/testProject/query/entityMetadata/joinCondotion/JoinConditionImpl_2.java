package rash.testProject.query.entityMetadata.joinCondotion;

import lombok.SneakyThrows;
import rash.testProject.query.entityHandler.joinMetadataHandler.JoinMetadataHandler;
import rash.testProject.query.entityMetadata.EntityColumn;
import rash.testProject.query.entityMetadata.JoinCondition;
import rash.testProject.query.entityMetadata.column.ColumnImpl;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.AccessibleObject;
import java.util.Arrays;

public class JoinConditionImpl_2 implements JoinCondition {
    private EntityColumn mainTableColumn;
    private EntityColumn secondaryTableColumn;

    public JoinConditionImpl_2(EntityColumn mainTableColumn, EntityColumn secondaryTableColumn) {
        this.mainTableColumn = mainTableColumn;
        this.secondaryTableColumn = secondaryTableColumn;
    }

    @Override
    public String getMainTableName() {
        return mainTableColumn.getTableName();
    }

    @Override
    public String getMainTableColumnName() {
        return mainTableColumn.getColumnName();
    }

    @Override
    public String getSecondaryTableName() {
        return secondaryTableColumn.getTableName();
    }

    @Override
    public String getSecondaryTableColumnName() {
        return secondaryTableColumn.getColumnName();
    }

    public static class Builder {
        private JoinMetadataHandler annotationHandler;

        private String mainTableName;
        private Class<?> joinEntityClass;
        private AccessibleObject property;
        private String propertyName;

        public Builder(JoinMetadataHandler annotationHandler) {
            this.annotationHandler = annotationHandler;
        }

        public Builder MainTableName(String mainTableName) {
            this.mainTableName = mainTableName;
            return this;
        }

        public Builder joinEntityClass(Class<?> joinEntityClass) {
            this.joinEntityClass = joinEntityClass;
            return this;
        }

        public Builder property(AccessibleObject property) {
            this.property = property;

            try {
                propertyName = (String) property.getClass().getMethod("getName").invoke(property);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return this;
        }

        public JoinConditionImpl_2 build() {

            String mainEntityTableColumnName = annotationHandler.getMainEntityTableColumnName(property);
            if (mainEntityTableColumnName == null || mainEntityTableColumnName.isEmpty()) {
                mainEntityTableColumnName = propertyName;
            }

            String mainEntityTableName = annotationHandler.getMainEntityTableName(property);
            if (mainEntityTableName == null || mainEntityTableName.isEmpty()) {
                mainEntityTableName = this.mainTableName;
            }

            String secondaryEntityTableName = annotationHandler.getSecondaryEntityTableName(property);
            if (secondaryEntityTableName == null || secondaryEntityTableName.isEmpty()) {
                Table annotation = joinEntityClass.getAnnotation(Table.class);
                secondaryEntityTableName = (annotation != null) ?
                        annotation.name() :
                        joinEntityClass.getSimpleName();
            }

            String secondaryEntityTableColumnName = annotationHandler.getSecondaryEntityTableColumnName(property);
            if (secondaryEntityTableColumnName == null || secondaryEntityTableColumnName.isEmpty()) {
                secondaryEntityTableColumnName = getAlternativeSecondaryTableColumnName(joinEntityClass);
            }

            return new JoinConditionImpl_2(
                    new ColumnImpl(mainEntityTableName, mainEntityTableColumnName),
                    new ColumnImpl(secondaryEntityTableName, secondaryEntityTableColumnName)
            );

        }

        @SneakyThrows
        private String getAlternativeSecondaryTableColumnName(Class<?> joinEntityClass) {
            AccessibleObject annotatedIdItem = null;
            annotatedIdItem = Arrays.stream( joinEntityClass.getDeclaredFields() )
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElse(null);
            if (annotatedIdItem == null) {
                annotatedIdItem = Arrays.stream( joinEntityClass.getDeclaredMethods() )
                        .filter(field -> field.isAnnotationPresent(Id.class))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Entity most have @Id"));
            }

            javax.persistence.Column columnAnnotation = annotatedIdItem.getAnnotation(javax.persistence.Column.class);
            return  (columnAnnotation != null) ?
                    columnAnnotation.name() :
                    (String) annotatedIdItem.getClass().getMethod("getName").invoke(annotatedIdItem);
        }

    }
}
