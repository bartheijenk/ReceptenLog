package persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

class EntityManagerProvider {
    private static EntityManager em;

    static EntityManager getEntityManager() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory("MySQL-recipelog").createEntityManager();
        }
        return em;
    }
}
