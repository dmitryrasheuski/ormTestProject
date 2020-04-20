package rash.testProject.query.entityMetadata;

public interface EntityMetadataBuilder {
    EntityMetadataBuilder entity(Class<?> entityClass);
    EntityMetadataBuilder joinCondition(JoinCondition condition);
    EntityMetadata build();
}
