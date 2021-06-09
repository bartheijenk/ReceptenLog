package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.CategorieDao;
import org.bartheijenk.persistence.dao.ReceptDao;
import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Recept;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategorieService implements ICategorieService {

    private final CategorieDao categorieDao;
    private final ReceptDao receptDao;

    @Inject
    public CategorieService(CategorieDao categorieDao, ReceptDao receptDao) {
        this.categorieDao = categorieDao;
        this.receptDao = receptDao;

    }
    public List<Categorie> getAllCategories() {
        return categorieDao.findAll();
    }

    public Optional<Categorie> getTagById(Long id) {
        return categorieDao.find(id);
    }

    public List<Recept> getReceptenOfCategorie(Categorie categorie) {
        return receptDao.getReceptenNaamOpIdPerCategorie(categorie);

    }
}
