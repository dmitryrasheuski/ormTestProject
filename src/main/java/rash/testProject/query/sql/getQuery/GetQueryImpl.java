package rash.testProject.query.sql.getQuery;

import rash.testProject.query.Context;
import rash.testProject.query.Expression;
import rash.testProject.query.Query;
import rash.testProject.query.entityMetadata.EntityMetadata;
import rash.testProject.query.entityMetadata.EntityMetadataBuilder;
import rash.testProject.query.sql.expression.FromExpression;
import rash.testProject.query.sql.expression.SelectExpression;
import rash.testProject.query.sql.expression.WhereExpression;
import rash.testProject.query.sql.expression.from.FromImpl;
import rash.testProject.query.sql.expression.select.SelectExpressionImpl;

public class GetQueryImpl implements Query {
    private EntityMetadata entityMetadata;
    private WhereExpression condition;
    private SelectExpression select;
    private FromExpression from;

    public GetQueryImpl(EntityMetadata entityMetadata) {
        this.entityMetadata = entityMetadata;
        this.select = new SelectExpressionImpl(entityMetadata);
        this.from = new FromImpl(entityMetadata);
    }
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void interpret(Context context) {
        from.interpret(context);
        select.interpret(context);
        if (condition != null) {
            condition.interpret(context);
        }
    }

    public static class Builder {
        private EntityMetadataBuilder entityMetadataBuilder;
        private Class<?> entityClass;
        private WhereExpression condition;

        public Builder entityMetadataBuilder(EntityMetadataBuilder entityMetadataBuilder) {
            this.entityMetadataBuilder = entityMetadataBuilder;
            return this;
        }
        public Builder entity(Class<?> entityClass) {
            this.entityClass = entityClass;
            return this;
        }
        public Builder condition(WhereExpression condition) {
            this.condition = condition;
            return this;
        }
        public GetQueryImpl build() {
            EntityMetadata entityMetadata = entityMetadataBuilder.entity(entityClass).build();
            GetQueryImpl query = new GetQueryImpl(entityMetadata);
            query.condition = condition;

            return query;
        }

    }
}
