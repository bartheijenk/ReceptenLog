package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.CategorieDao;
import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import java.util.List;

public class CategorieService {
    private static CategorieService instance;

    public static CategorieService getInstance() {
        if (instance == null) {
            instance = new CategorieService();
        }
        return instance;
    }

    private CategorieDao categorieDao = CategorieDao.getInstance(EntityManagerProvider.getEntityManager());

    private CategorieService() {

    }

    private CategorieService(EntityManager em) {
        categorieDao = CategorieDao.getInstance(em);
    }

    /**
     * Gives a list of all tags
     *
     * @return a List of Tag objects
     */
    public List<Categorie> getAllCategories() {
        return categorieDao.findAll();
    }

    /**
     * Gives a Tag object with specified ID
     *
     * @param id the ID in Long
     * @return A Tag object or null
     */
    public Categorie getTagById(Long id) {
        return categorieDao.find(id);
    }
}
