package org.bartheijenk.persistence.dao;

import lombok.extern.log4j.Log4j2;
import org.bartheijenk.persistence.entity.Categorie;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Log4j2
public class CategorieDao extends Dao<Categorie, Long> {

    private static CategorieDao instance;

    private CategorieDao(EntityManager em) {
        super(em);
    }

    public static CategorieDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new CategorieDao(em);
        }
        return instance;
    }

    public void saveIfNotExists(Categorie categorie) {
        if (findByNaam(categorie.getNaam()) == null) {
            save(categorie);
        }
    }

    public Categorie findByNaam(String naam) {
        TypedQuery<Categorie> query = em.createQuery("select i from Categorie i where i.naam=:naam", Categorie.class);
        query.setParameter("naam", naam);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public List<Categorie> findAllByNaam(List<String> categorieNames) {
        TypedQuery<Categorie> query = em.createQuery("select t from Categorie t where t.naam in :categorieNames", Categorie.class);
        query.setParameter("categorieNames", categorieNames);
        return query.getResultList();
    }

    public List<Categorie> findAllById(List<Long> categorieIds) {
        TypedQuery<Categorie> query = em.createQuery("select t from Categorie t where t.id in :categorieIds", Categorie.class);
        query.setParameter("categorieIds", categorieIds);
        return query.getResultList();
    }
}
