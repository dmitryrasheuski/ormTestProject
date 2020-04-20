package rash.testProject.query.entityMetadata.builder;

import lombok.SneakyThrows;
import rash.testProject.query.entityHandler.columnMetadataHandler.ColumnMetadataHandler;
import rash.testProject.query.entityHandler.joinMetadataHandler.JoinMetadataHandler;
import rash.testProject.query.entityMetadata.EntityColumn;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.entityMetadata.JoinCondition;
import rash.testProject.query.entityMetadata.column.ColumnImpl;
import rash.testProject.query.entityMetadata.entityMetadata.JoinEntity;
import rash.testProject.query.entityMetadata.entityMetadata.MainEntity;
import rash.testProject.query.entityMetadata.joinCondotion.JoinConditionImpl_2;

import javax.persistence.*;
import java.lang.reflect.AccessibleObject;
import java.util.*;
import java.util.stream.Collectors;

public class NewEntityMetadataBuilder extends AbstractEntityMetadataBuilder {
    private ColumnMetadataHandler columnMetadataHandler;
    private JoinMetadataHandler joinHandler;

    public NewEntityMetadataBuilder(ColumnMetadataHandler columnMetadataHandler, JoinMetadataHandler joinHandler) {
        this.columnMetadataHandler = columnMetadataHandler;
        this.joinHandler = joinHandler;
    }

    public EntityMetadata build() {

        Table table = entityClass.getAnnotation(Table.class);
        String mainTableName = (table != null) ? table.name() : entityClass.getSimpleName();

        List<AccessibleObject> persistentProperties = getPersistentProperties(accessType, entityClass);
        List<AccessibleObject> persistentEntityTypeProperties = persistentProperties.stream()
                .filter( field -> getType(field).isAnnotationPresent(Entity.class) || Arrays.asList( getType(field).getInterfaces() ).contains(Collection.class) )
                .collect(Collectors.toList());

        checkOfPersistentEntityTypeFields(persistentEntityTypeProperties);

        Map<String, JoinCondition> secondaryTable = getSecondaryTables(mainTableName);
        Map<String, JoinCondition> thirdTable = getThirdTable(mainTableName, persistentEntityTypeProperties);
        secondaryTable.putAll(thirdTable);

        List<EntityColumn> columns = getColumns(mainTableName, persistentProperties);
        List<EntityMetadata> joinedTables = getJoinedTables(mainTableName, persistentEntityTypeProperties);

        return (joinCondition == null) ?
                new MainEntity(entityClass, mainTableName,secondaryTable, columns, joinedTables) :
                new JoinEntity(entityClass, mainTableName,secondaryTable, columns, joinedTables, joinCondition);

    }

    @SneakyThrows
    protected List<EntityColumn> getColumns(String mainTableName, List<AccessibleObject> properties) {
        ArrayList<EntityColumn> columnList = new ArrayList<>();

        EntityColumn column = null;
        String tableName = null;
        String columnName = null;
        for (AccessibleObject property : properties) {

            columnName = columnMetadataHandler.getColumnName(property);
            if (columnName == null || columnName.equals("")) {
                    columnName = (String) property.getClass().getDeclaredMethod("getName").invoke(property);
            }

            tableName = columnMetadataHandler.getTableName(property);
            if (tableName == null || tableName.equals("")) {
                tableName = mainTableName;
            }

            column = new ColumnImpl(tableName, columnName);
            columnList.add(column);

        }

        return columnList;
    }

    @SneakyThrows
    protected List<EntityMetadata> getJoinedTables(String mainTableName, List<AccessibleObject> entityTypeProperties) {
        List<EntityMetadata> tables = new ArrayList<>();

        EntityMetadata joinEntity = null;
        Class<?> joinEntityClass = null;
        for (AccessibleObject property : entityTypeProperties) {

            joinEntityClass = (isCollection(property) && isGeneric(property)) ?
                    getGenericType(property) :
                    getType(property);

            joinEntity = new NewEntityMetadataBuilder(columnMetadataHandler, joinHandler)
                    .entity(joinEntityClass)
                    .joinCondition(
                            new JoinConditionImpl_2.Builder(joinHandler)
                            .MainTableName(mainTableName)
                            .joinEntityClass(joinEntityClass)
                            .property(property)
                            .build())
                    .build();

            tables.add(joinEntity);
        }
        return tables;
    }

}
