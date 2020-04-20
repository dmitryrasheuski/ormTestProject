package rash.testProject.query.entityMetadata.joinCondotion;

import lombok.SneakyThrows;
import rash.testProject.query.entityMetadata.JoinCondition;

import javax.persistence.*;
import java.lang.reflect.AccessibleObject;
import java.util.Arrays;

public class JoinConditionImpl_1 implements JoinCondition {
    protected String mainTableColumnName;
    protected String secondaryTableColumnName;
    protected String mainTableName;
    protected String secondaryTableName;

    public JoinConditionImpl_1(String mainTableName, String mainTableColumnName, String secondaryTableName, String secondaryTableColumnName) {
        this.mainTableColumnName = mainTableColumnName;
        this.secondaryTableColumnName = secondaryTableColumnName;
        this.mainTableName = mainTableName;
        this.secondaryTableName = secondaryTableName;
    }

    @Override
    public String getMainTableColumnName() {
        return mainTableColumnName;
    }

    @Override
    public String getSecondaryTableColumnName() {
        return secondaryTableColumnName;
    }

    @Override
    public String getMainTableName() {
        return mainTableName;
    }

    @Override
    public String getSecondaryTableName() {
        return secondaryTableName;
    }

    public static class Builder {
        private Class<?> dependentEntityClass;
        private AccessibleObject property;
        private String propertyName;
        private String mainTableAlias;

        public Builder mainTableAlias(String alias) {
            if (mainTableAlias == null) {
                mainTableAlias = alias;
            }
            return this;
        }
        public Builder secondaryEntityClass(Class<?> dependentEntityClass) {
            this.dependentEntityClass = dependentEntityClass;
            return this;
        }
        public Builder foreignKey(AccessibleObject property) {
            this.property = property;

            try {
                propertyName = (String) property.getClass().getMethod("getName").invoke(property);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (property.isAnnotationPresent(JoinTable.class)) {
                JoinTable joinTable = property.getAnnotation(JoinTable.class);
                mainTableAlias = joinTable.name();
            }
            return this;
        }
        public JoinConditionImpl_1 build() {
            String foreignKeyColumnNameFromMainTable = getColumnNameFromMainTable(property);
            String columnName = getColumnName(property);
            String tableAlias = getJoinedTableAlias(property);
            return new JoinConditionImpl_1(mainTableAlias, foreignKeyColumnNameFromMainTable, tableAlias, columnName);
        }

        //сделать цепочку обработчиков

// этот код 3 методов в одном (почти весь)
//            if (property.isAnnotationPresent(JoinTable.class)) {
//
//                JoinTable joinTableAnn = property.getAnnotation(JoinTable.class);
//                JoinColumn joinColumnAnn = joinTableAnn.inverseJoinColumns()[0];
//
//                joiningCondition.tableAlias = joinTableAnn.name();
//                joiningCondition.columnName = joinColumnAnn.name();
//                joiningCondition.foreignKeyColumnNameFromMainTable = joinColumnAnn.referencedColumnName();
//
//            } else if (property.isAnnotationPresent(JoinColumns.class)) {
//
//            } else if (property.isAnnotationPresent(JoinColumn.class)) {
//                JoinColumn joinColumnAnn = property.getAnnotation(JoinColumn.class);
//                joiningCondition.tableAlias = joinColumnAnn.name();
//                joiningCondition.columnName = joinColumnAnn.name();
//                joiningCondition.foreignKeyColumnNameFromMainTable = joinColumnAnn.referencedColumnName();
//            } else {
//                joiningCondition.foreignKeyColumnNameFromMainTable = propertyName;
//
//                AccessibleObject annotatedIdItem = null;
//                annotatedIdItem = Arrays.stream( dependentEntityClass.getDeclaredFields() )
//                        .filter(field -> field.isAnnotationPresent(Id.class))
//                        .findFirst()
//                        .orElse(null);
//                if (annotatedIdItem == null) {
//                    annotatedIdItem = Arrays.stream( dependentEntityClass.getDeclaredMethods() )
//                            .filter(field -> field.isAnnotationPresent(Id.class))
//                            .findFirst()
//                            .orElseThrow(() -> new RuntimeException("Entity most have @Id"));
//                }
//
//                Column columnAnnotation = annotatedIdItem.getAnnotation(Column.class);
//                joiningCondition.columnName = (columnAnnotation != null) ?
//                        columnAnnotation.name() :
//                        (String) annotatedIdItem.getClass().getMethod("getName").invoke(annotatedIdItem);
//
//                Table annotation = dependentEntityClass.getAnnotation(Table.class);
//                joiningCondition.tableAlias = (annotation != null)  ? annotation.name() : dependentEntityClass.getSimpleName();

//            }

        protected String getColumnName(AccessibleObject property) {
            String referencedColumnName = null;
            if (property.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn annotation = property.getAnnotation(JoinColumn.class);
                referencedColumnName = annotation.referencedColumnName();
            } else if (property.isAnnotationPresent(JoinTable.class)) {
                JoinTable joinTableAnn = property.getAnnotation(JoinTable.class);
                JoinColumn joinColumnAnn = joinTableAnn.inverseJoinColumns()[0];
                referencedColumnName = joinColumnAnn.referencedColumnName();
            }

            if (referencedColumnName == null || referencedColumnName.isEmpty()) {
                referencedColumnName = propertyName;
            }
            return referencedColumnName;
        }
        @SneakyThrows
        protected String getColumnNameFromMainTable(AccessibleObject property) {
            String referencedColumnName = null;
            if (property.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn annotation = property.getAnnotation(JoinColumn.class);
                referencedColumnName = annotation.name();
            } else if (property.isAnnotationPresent(JoinTable.class)) {
                JoinTable joinTableAnn = property.getAnnotation(JoinTable.class);
                JoinColumn joinColumnAnn = joinTableAnn.inverseJoinColumns()[0];
                referencedColumnName = joinColumnAnn.name();
            }

            if (referencedColumnName == null || referencedColumnName.isEmpty()) {

                AccessibleObject annotatedIdItem = null;
                annotatedIdItem = Arrays.stream( dependentEntityClass.getDeclaredFields() )
                        .filter(field -> field.isAnnotationPresent(Id.class))
                        .findFirst()
                        .orElse(null);
                if (annotatedIdItem == null) {
                    annotatedIdItem = Arrays.stream( dependentEntityClass.getDeclaredMethods() )
                            .filter(field -> field.isAnnotationPresent(Id.class))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Entity most have @Id"));
                }

                Column columnAnnotation = annotatedIdItem.getAnnotation(Column.class);
                referencedColumnName = (columnAnnotation != null) ?
                        columnAnnotation.name() :
                        (String) annotatedIdItem.getClass().getMethod("getName").invoke(annotatedIdItem);

            }

            return referencedColumnName;
        }
        protected String getJoinedTableAlias(AccessibleObject property) {
            String joinedTableAlias = null;
            if (property.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn annotation = property.getAnnotation(JoinColumn.class);
                joinedTableAlias = annotation.table();
            } else if (property.isAnnotationPresent(JoinTable.class)) {
                JoinTable joinTableAnn = property.getAnnotation(JoinTable.class);
                JoinColumn joinColumnAnn = joinTableAnn.inverseJoinColumns()[0];
                joinedTableAlias = joinColumnAnn.table();
            }

            if (joinedTableAlias == null || joinedTableAlias.isEmpty()) {
                Table annotation = dependentEntityClass.getAnnotation(Table.class);
                joinedTableAlias = (annotation != null)  ? annotation.name() : dependentEntityClass.getSimpleName();
            }
            return joinedTableAlias;
        }
    }
}
