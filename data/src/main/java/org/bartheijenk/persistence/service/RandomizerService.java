package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.CategorieDao;
import org.bartheijenk.persistence.dao.ReceptDao;
import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import java.util.List;
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

    /**
     * Returns a random map of recipes based on provided tags
     *
     * @param limit  How many recipes should be gotten
     * @param tagIds A List of Long Ids of tags
     * @return returns a Map of Long Ids and String names of recipes or null if no tags are found for the IDs
     */
    public Map<Long, String> getRandomizedListWithCategories(int limit, List<Long> tagIds) {
        List<Categorie> categories = CategorieDao.getInstance(EntityManagerProvider.getEntityManager()).findAllById(tagIds);
        if (categories.isEmpty())
            return null;
        else
            return receptDao.getRandomRecepten(limit, categories);
    }
}
