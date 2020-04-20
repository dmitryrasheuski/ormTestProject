package rash.testProject.dao.datasource;

import lombok.SneakyThrows;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import java.io.FileReader;
import java.util.Properties;

public class HibernateDataSourceImpl implements Datasource {
    private static final HibernateDataSourceImpl INSTANCE;
    static {
        INSTANCE = new HibernateDataSourceImpl();
    }

    private EntityManagerFactory entityManagerFactory;

    public static HibernateDataSourceImpl getInstance(){
        return INSTANCE;
    }
    private HibernateDataSourceImpl() {
        entityManagerFactory = getConfiguration().buildSessionFactory();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @SneakyThrows
    private Configuration getConfiguration() {

        Properties properties = new Properties();
        properties.load(new FileReader("./src/main/resources/dataSource.properties"));

        return new Configuration().configure("hibernateConf.xml")
                .setProperties(properties);
    }
}
