package rash.testProject.query.entityMetadata.builder;


import lombok.SneakyThrows;
import rash.testProject.query.entityMetadata.EntityColumn;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.entityMetadata.JoinCondition;
import rash.testProject.query.entityMetadata.column.ColumnImpl;
import rash.testProject.query.entityMetadata.entityMetadata.JoinEntity;
import rash.testProject.query.entityMetadata.entityMetadata.MainEntity;
import rash.testProject.query.entityMetadata.joinCondotion.JoinConditionImpl_1;

import javax.persistence.*;
import java.lang.reflect.AccessibleObject;
import java.util.*;
import java.util.stream.Collectors;

public class EntityMetadataBuilderImpl extends AbstractEntityMetadataBuilder {
    protected List<AccessibleObject> persistentFields;
    protected List<AccessibleObject> persistentEntityTypeFields;
    protected String mainTableName;

    public EntityMetadata build() {

        Table table = entityClass.getAnnotation(Table.class);
        mainTableName = (table != null) ? table.name() : entityClass.getSimpleName();

        persistentFields = getPersistentProperties(accessType, entityClass);
        persistentEntityTypeFields = persistentFields.stream()
                .filter( field -> getType(field).isAnnotationPresent(Entity.class) || Arrays.asList( getType(field).getInterfaces() ).contains(Collection.class) )
                .collect(Collectors.toList());

        checkOfPersistentEntityTypeFieldsOnCorrectAnnotating();
        Map<String, JoinCondition>  secondaryTable = secondaryTableAnnotationHandler();

        List<EntityColumn> columns = getColumns();
        List<EntityMetadata> joinedTables = getJoinedTables();

        return (joinCondition == null) ?
                new MainEntity(entityClass, mainTableName,secondaryTable, columns, joinedTables) :
                new JoinEntity(entityClass, mainTableName,secondaryTable, columns, joinedTables, joinCondition);

    }

    protected void checkOfPersistentEntityTypeFieldsOnCorrectAnnotating() {
        long count = persistentEntityTypeFields.stream()
                .filter(field -> ! field.isAnnotationPresent(OneToOne.class))
                .filter(field -> ! field.isAnnotationPresent(ManyToMany.class))
                .filter(field -> ! field.isAnnotationPresent(ManyToOne.class))
                .filter(field -> ! field.isAnnotationPresent(OneToMany.class))
                .count();
        if ( count > 0 ) throw new RuntimeException("secondary entity doesn't have annotation (@OneToOne || @ManyToMany || @ManyToOne || @OneToMany) ");
    }
    protected Map<String, JoinCondition> secondaryTableAnnotationHandler () {
        Map<String, JoinCondition> map = new HashMap<>();

        List<SecondaryTable> secondaryTables =new ArrayList<>();
        SecondaryTables secondaryTablesAnn = entityClass.getAnnotation(SecondaryTables.class);
        if (secondaryTablesAnn != null) secondaryTables.addAll( Arrays.asList( secondaryTablesAnn.value() ) );
        SecondaryTable secondaryTableAnn = entityClass.getAnnotation(SecondaryTable.class);
        if (secondaryTableAnn != null) secondaryTables.add(secondaryTableAnn);

        for (SecondaryTable table : secondaryTables) {

            String secondaryTableName = table.name();
            PrimaryKeyJoinColumn primaryKeyJoinColumn = table.pkJoinColumns()[0];
            String mainTableColumnName = primaryKeyJoinColumn.referencedColumnName();
            String secondaryTableColumnName = primaryKeyJoinColumn.name();

            JoinCondition condition = new JoinConditionImpl_1(mainTableName, mainTableColumnName, secondaryTableName, secondaryTableColumnName);
            map.put(secondaryTableName, condition);

        }

        for (AccessibleObject property : persistentEntityTypeFields) {
            if ( property.isAnnotationPresent(JoinTable.class) ) {
                JoinTable joinTableAnn = property.getAnnotation(JoinTable.class);
                JoinColumn joinColumnAnn = joinTableAnn.joinColumns()[0];

                String secondaryTableName = joinTableAnn.name();
                String secondaryTableColumnName = joinColumnAnn.name();
                String mainTableColumnName = joinColumnAnn.referencedColumnName();

                JoinCondition condition = new JoinConditionImpl_1(mainTableName, mainTableColumnName, secondaryTableName, secondaryTableColumnName);
                map.put(secondaryTableName, condition);

            }
        }


        return map;
    }

    @SneakyThrows
    protected List<EntityColumn> getColumns() {
        List<EntityColumn> columns = new ArrayList<>();
        EntityColumn currentColumn = null;
        javax.persistence.Column annotation;
        for (AccessibleObject field : persistentFields) {

            Class<?> joinEntityClass = (isCollection(field) && isGeneric(field)) ?
                    getGenericType(field) :
                    getType(field);

            if ( joinEntityClass.isAnnotationPresent(Entity.class) ) {
                //currentColumn = getColumnNameForEntityColumn(field);
                //columns.add(currentColumn);
            } else {
                String tableName = null;
                String columnName = null;
                if (field.isAnnotationPresent(javax.persistence.Column.class)) {
                    annotation = field.getAnnotation(javax.persistence.Column.class);
                    columnName = annotation.name();
                    tableName = annotation.table();
                }

                if (columnName == null || columnName.equals("")) {
                    columnName = (String) field.getClass().getDeclaredMethod("getName").invoke(field);
                }

                if (tableName == null || tableName.equals("")) {
                    tableName = mainTableName;
                }
                currentColumn = new ColumnImpl(tableName, columnName);
                columns.add(currentColumn);
            }
        }
        return columns;
    }

    @SneakyThrows
    protected EntityColumn getColumnNameForEntityColumn(AccessibleObject field) {

        String columnName = null;
        String tableName = null;
        JoinTable joinTable = field.getAnnotation(JoinTable.class);
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);

        if (joinColumn != null) {
            columnName = joinColumn.name();
            tableName = joinColumn.table();
        }
        else if (joinColumns != null) {
            columnName = joinColumns.value()[0].name();
            tableName = joinColumns.value()[0].table();
        }
        else if (joinTable != null) {
            columnName = joinTable.joinColumns()[0].referencedColumnName();//name()
            tableName = joinTable.joinColumns()[0].table();
        }
        else {
            columnName = (String) field.getClass().getDeclaredMethod("getName").invoke(field);
            tableName = mainTableName;
        }

        if (tableName == null || tableName.equals("")) tableName = mainTableName;

        return new ColumnImpl(tableName, columnName);
    }

    @SneakyThrows
    protected List<EntityMetadata> getJoinedTables() {
        List<EntityMetadata> tables = new ArrayList<>();

        for (AccessibleObject field : persistentEntityTypeFields) {

            Class<?> joinEntityClass = (isCollection(field) && isGeneric(field)) ?
                    getGenericType(field) :
                    getType(field);

            EntityMetadata joinedTable = new EntityMetadataBuilderImpl()
                    .entity( joinEntityClass )
                    .joinCondition(new JoinConditionImpl_1.Builder()
                            .mainTableAlias(mainTableName)
                            .secondaryEntityClass(joinEntityClass)
                            .foreignKey(field)
                            .build())
                    .build();

            tables.add(joinedTable);
        }
        return tables;
    }
}
