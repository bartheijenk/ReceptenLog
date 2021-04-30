package org.bartheijenk.persistence.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    private static EntityManager em;

    public static EntityManager getEntityManager() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory("MySQL-recipelog").createEntityManager();
        }
        return em;
    }
}
