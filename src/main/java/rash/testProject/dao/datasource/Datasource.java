package rash.testProject.dao.datasource;

import javax.persistence.EntityManagerFactory;

public interface Datasource {
    EntityManagerFactory getEntityManagerFactory();
}
