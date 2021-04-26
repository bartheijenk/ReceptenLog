package persistence;

import javax.persistence.EntityManager;

public class DataInitializer {
    public static void start() {
        EntityManager em = EntityManagerProvider.getEntityManager();
    }
}
