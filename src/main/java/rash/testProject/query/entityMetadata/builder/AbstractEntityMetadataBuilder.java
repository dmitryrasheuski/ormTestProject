package rash.testProject.query.entityMetadata.builder;

import rash.testProject.query.entityMetadata.EntityMetadataBuilder;
import rash.testProject.query.entityMetadata.JoinCondition;
import rash.testProject.query.entityMetadata.joinCondotion.JoinConditionImpl_1;

import javax.persistence.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractEntityMetadataBuilder implements EntityMetadataBuilder {
    protected Class<?> entityClass;
    protected AccessType accessType;
    protected JoinCondition joinCondition;

    public AbstractEntityMetadataBuilder entity(Class<?> entityClazz) {
        this.entityClass = entityClazz;
        this.accessType = getAccessType(entityClazz);
        entityClassCheck(entityClazz);
        return this;
    }
    public AbstractEntityMetadataBuilder joinCondition(JoinCondition joinCondition) {
        this.joinCondition = joinCondition;
        return this;
    }

    protected void entityClassCheck(Class<?> entityClazz) {

        if ( ! entityClazz.isAnnotationPresent(Entity.class) ) {
            throw new RuntimeException("Obtained class does'n annotated @Entity");
        }

        boolean idIsPresent = ( accessType.equals(AccessType.FIELD) ) ?
                Arrays.stream( entityClazz.getDeclaredFields() )
                        .anyMatch(field -> field.isAnnotationPresent(Id.class)) :
                Arrays.stream( entityClazz.getDeclaredMethods() )
                        .anyMatch(field -> field.isAnnotationPresent(Id.class));

        if ( ! idIsPresent ) throw new RuntimeException("Entity doesn't have @Id");
    }
    protected Class<?> getType(AccessibleObject accessible) {
        return (accessible instanceof Field) ?
                ((Field) accessible).getType() :
                ((Method) accessible).getReturnType();
    }
    protected AccessType getAccessType(Class<?> entityClazz) {
        return Arrays.stream( entityClazz.getDeclaredFields() )
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .map(field -> AccessType.FIELD)
                .orElse(AccessType.PROPERTY);
    }
    protected boolean isCollection(AccessibleObject properties) {
        Class<?> propertyType = (properties instanceof Field) ?
                ((Field) properties).getType() :
                ((Method) properties).getReturnType();
        return Arrays.asList( propertyType.getInterfaces() )
                .contains(Collection.class);
    }
    protected Class<?> getGenericType(AccessibleObject properties) throws ClassNotFoundException {
        String typeName = (properties instanceof Field) ?
                ((ParameterizedType)
                        ((Field) properties).getGenericType()
                ).getActualTypeArguments()[0].getTypeName()
                :
                ((ParameterizedType)
                        ((Method) properties).getGenericReturnType()
                ).getActualTypeArguments()[0].getTypeName();
        return Class.forName(typeName);
    }
    protected boolean isGeneric(AccessibleObject property) {
        Type type = (property instanceof Field) ?
                ((Field) property).getGenericType() :
                ((Method) property).getGenericReturnType();
        return (type instanceof ParameterizedType);
    }
    protected List<AccessibleObject> getPersistentProperties(AccessType access, Class<?> entityClazz) {

        return  (access.equals(AccessType.FIELD)) ?
                Arrays.stream(entityClazz.getDeclaredFields())
                        .filter(field -> ! field.isAnnotationPresent(Transient.class))
                        .collect(Collectors.toList())
                :
                Arrays.stream(entityClazz.getDeclaredMethods())
                        .filter(method -> ! method.isAnnotationPresent(Transient.class))
                        .filter(method -> method.getName().matches("get.*"))
                        .collect(Collectors.toList());
    }
    protected Map<String, JoinCondition> getSecondaryTables(String mainTableName) {
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
        return map;
    }
    protected Map<String, JoinCondition> getThirdTable(String mainTableName, List<AccessibleObject> properties) {
        Map<String, JoinCondition> map = new HashMap<>();

        for (AccessibleObject property : properties) {

            if ( ! property.isAnnotationPresent(JoinTable.class) ) continue;

            JoinTable joinTableAnn = property.getAnnotation(JoinTable.class);
            JoinColumn joinColumnAnn = joinTableAnn.joinColumns()[0];

            String secondaryTableName = joinTableAnn.name();
            String secondaryTableColumnName = joinColumnAnn.name();
            String mainTableColumnName = joinColumnAnn.referencedColumnName();

            JoinCondition condition = new JoinConditionImpl_1(mainTableName, mainTableColumnName, secondaryTableName, secondaryTableColumnName);
            map.put(secondaryTableName, condition);
        }
        return map;
    }
    protected void checkOfPersistentEntityTypeFields(List<AccessibleObject> persistentEntityTypeProperties) {
        long count = persistentEntityTypeProperties.stream()
                .filter(field -> ! field.isAnnotationPresent(OneToOne.class))
                .filter(field -> ! field.isAnnotationPresent(ManyToMany.class))
                .filter(field -> ! field.isAnnotationPresent(ManyToOne.class))
                .filter(field -> ! field.isAnnotationPresent(OneToMany.class))
                .count();
        if ( count > 0 ) throw new RuntimeException("secondary entity doesn't have annotation (@OneToOne || @ManyToMany || @ManyToOne || @OneToMany) ");
    }
}
