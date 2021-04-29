package persistence;

import persistence.dao.ReceptDao;

import javax.persistence.EntityManager;
import java.util.Map;

public class RandomizerService {
    private static RandomizerService instance;

    public static RandomizerService getInstance() {
        if (instance == null)
            instance = new RandomizerService(EntityManagerProvider.getEntityManager());
        return instance;
    }

    public RandomizerService(EntityManager em) {
        receptDao = ReceptDao.getInstance(em);
    }

    private final ReceptDao receptDao;


    /**
     * Returns a random map of recipes
     *
     * @param limit how many recipes should be gotten
     * @return a Map of Long Ids with String names of recipes
     */
    public Map<Long, String> getRandomizedList(int limit) {
        return receptDao.getRandomRecepten(limit);
    }
}
