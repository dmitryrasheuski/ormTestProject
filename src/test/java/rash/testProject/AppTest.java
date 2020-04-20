package rash.testProject;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rash.testProject.dao.datasource.HibernateDataSourceImpl;
import rash.testProject.entity.curriculumVitae.CurriculumVitae;
import rash.testProject.query.Parameter;
import rash.testProject.query.Query;
import rash.testProject.query.entityHandler.annotationHandler.columnAnnotationHandler.ColumnAnnotationHandlerImpl;
import rash.testProject.query.entityHandler.annotationHandler.joinColumnAnnotationHandler.JoinColumnAnnotationHandlerImpl;
import rash.testProject.query.entityHandler.annotationHandler.joinTableAnnotationHandler.JoinTableAnnotationHandlerImpl;
import rash.testProject.query.entityHandler.columnMetadataHandler.ColumnMetadataHandler;
import rash.testProject.query.entityHandler.columnMetadataHandler.ColumnMetadataHandlerImpl;
import rash.testProject.query.entityHandler.columnMetadataHandler.item.JoinColumnMetadataHandler;
import rash.testProject.query.entityHandler.columnMetadataHandler.item.JoinTableMetadataHandler;
import rash.testProject.query.entityHandler.joinMetadataHandler.JoinMetadataHandler;
import rash.testProject.query.entityHandler.joinMetadataHandler.JoinMetadataHandlerImpl;
import rash.testProject.query.entityHandler.joinMetadataHandler.item.JoinColumnMetadataHandlerItem;
import rash.testProject.query.entityHandler.joinMetadataHandler.item.JoinTableMetadataHandlerItem;
import rash.testProject.query.entityMetadata.EntityMetadataBuilder;
import rash.testProject.query.entityMetadata.builder.NewEntityMetadataBuilder;
import rash.testProject.query.sql.expression.where.builder.WhereExpressionBuilder;
import rash.testProject.query.sql.expression.where.terminal.EqualsCondition;
import rash.testProject.query.sql.expression.where.terminal.LikeCondition;
import rash.testProject.query.sql.getQuery.GetQueryContextImpl;
import rash.testProject.query.sql.getQuery.GetQueryImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppTest {
    private static EntityManagerFactory entityManagerFactory;
    private static ColumnMetadataHandler columnMetadataHandler;
    private static JoinMetadataHandler joinHandler;

    private EntityMetadataBuilder entityMetadataBuilder;

    @BeforeClass
    public static void before() throws Exception{
        HibernateDataSourceImpl dataSource = HibernateDataSourceImpl.getInstance();
        entityManagerFactory = dataSource.getEntityManagerFactory();
        columnMetadataHandler = new ColumnMetadataHandlerImpl(
                new rash.testProject.query.entityHandler.columnMetadataHandler.item.ColumnMetadataHandler(new ColumnAnnotationHandlerImpl()),
                new JoinColumnMetadataHandler(new JoinColumnAnnotationHandlerImpl()),
                new JoinTableMetadataHandler(new JoinTableAnnotationHandlerImpl())
        );
        joinHandler = new JoinMetadataHandlerImpl(
                new JoinColumnMetadataHandlerItem(new JoinColumnAnnotationHandlerImpl()),
                new JoinTableMetadataHandlerItem(new JoinTableAnnotationHandlerImpl())
        );
    }

    @Before
    public void setUp() throws Exception {
        entityMetadataBuilder = new NewEntityMetadataBuilder(columnMetadataHandler, joinHandler);
    }

    @AfterClass
    public static void after() throws SQLException {
        entityManagerFactory.close();
    }

    @Test
    public void test_1() throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = GetQueryImpl.builder()
                .entityMetadataBuilder(entityMetadataBuilder)
                .entity(CurriculumVitae.class)
                .condition(
                        new WhereExpressionBuilder()
                                .condition( new EqualsCondition("person.first_name", "Мария") )
                                .and( new EqualsCondition("person.second_name", "Морская") )
                                .and( new EqualsCondition("person.patronymic", "Васильевна") )
                                .build()
                )
                .build();

        GetQueryContextImpl context = new GetQueryContextImpl();
        query.interpret(context);

        String executableString = context.getExecutableString();
        System.out.println(executableString);
        List<Parameter> parameters = context.getParameters();
        parameters.forEach(System.out::println);

        javax.persistence.Query nativeQuery = entityManager.createNativeQuery(executableString, CurriculumVitae.class);
        Set<CurriculumVitae> set = new HashSet<>( nativeQuery.getResultList() );
        set.forEach(System.out::println);

    }

    @Test
    public void test_2() throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = GetQueryImpl.builder()
                .entityMetadataBuilder(entityMetadataBuilder)
                .entity(CurriculumVitae.class)
                .condition(
                        new WhereExpressionBuilder()
                                .condition(new LikeCondition("person.second_name", "%ов"))
                                .or(new EqualsCondition("gender.title", "female"))
                                .build() )
                .build();

        GetQueryContextImpl context = new GetQueryContextImpl();
        query.interpret(context);

        String executableString = context.getExecutableString();
        System.out.println(executableString);
        List<Parameter> parameters = context.getParameters();
        parameters.forEach(System.out::println);

        javax.persistence.Query nativeQuery = entityManager.createNativeQuery(executableString, CurriculumVitae.class);
        Set<CurriculumVitae> set = new HashSet<>( nativeQuery.getResultList() );
        set.forEach(System.out::println);

    }

}
