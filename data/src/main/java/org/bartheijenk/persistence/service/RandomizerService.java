package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.CategorieDao;
import org.bartheijenk.persistence.dao.ReceptDao;
import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Recept;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class RandomizerService implements IRandomizerService {

    @Inject
    private ReceptDao receptDao;

    @Inject
    private CategorieDao categorieDao;

    public List<Recept> getRandomizedList(int limit) {
        return receptDao.getRandomRecepten(limit);
    }

    public List<Recept> getRandomizedListWithCategories(int limit, List<Long> tagIds) {
        List<Categorie> categories = categorieDao.findAllById(tagIds);
        if (categories.isEmpty())
            return null;
        else
            return receptDao.getRandomRecepten(limit, categories);
    }
}
