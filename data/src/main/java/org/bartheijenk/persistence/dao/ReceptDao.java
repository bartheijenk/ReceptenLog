package org.bartheijenk.persistence.dao;

import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Recept;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReceptDao extends Dao<Recept, Long> {

    public List<Recept> getReceptenNaamOpId() {
        return em.createQuery("SELECT r FROM Recept r", Recept.class).getResultList();
    }

    public List<Recept> getReceptenNaamOpIdPerCategorie(Categorie categorie) {
        TypedQuery<Recept> query = em.createQuery("SELECT r FROM Recept r where :categorie member of r.categories",
                Recept.class);
        query.setParameter("categorie", categorie);
        return query.getResultList();
    }

    public List<Recept> getRandomRecepten(int limit) {
        //MySQL specific query!!!!
        Query query = em.createNativeQuery("SELECT * FROM recipelog.recept r order by " +
                "RAND() LIMIT :limit");
        query.setParameter("limit", limit);
        return query.getResultList();
    }

    public List<Recept> getRandomRecepten(int limit, List<Categorie> categories) {
        //MySQL specific query!!!!
        StringBuilder s = new StringBuilder("SELECT r.id, r.titel FROM recipelog.recept r " +
                "WHERE r.id in (" +
                "SELECT rt.recept_id FROM recipelog.recept_categorie rt where");
        for (int i = 0; i < categories.size(); i++) {
            if (i == 0)
                s.append(" rt.categorie_id = ").append(categories.get(i).getId().toString());
            else
                s.append(" or rt.categorie_id = ").append(categories.get(i).getId().toString());
        }

        s.append(") order by RAND() LIMIT ").append(limit);
        Query query = em.createNativeQuery(s.toString());

        return query.getResultList();
    }
}
