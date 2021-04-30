package org.bartheijenk.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class TestEntityManagerProvider {

    static EntityManager entityManager = Persistence.createEntityManagerFactory("H2-test").createEntityManager();
}
