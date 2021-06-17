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

    @SuppressWarnings("unchecked")
    public List<Recept> getRandomRecepten(int limit) {
        //MySQL specific query!!!!
        Query query = em.createNativeQuery("SELECT * FROM recipelog.recept r order by " +
                "RAND() LIMIT " + limit, Recept.class);
//        query.setParameter("limit", limit);
        return (List<Recept>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Recept> getRandomRecepten(int limit, List<Categorie> categories) {

        /*SELECT r.* FROM recipelog.recept r
join recept_categorie rc on (r.id = rc.recept_id)
where rc.categorie_id in (select c.id from categorie c where c.id = 7 or c.id = 9)
group by r.id
having count(distinct rc.categorie_id) = 2*/

        //MySQL specific query!!!!
        StringBuilder s = new StringBuilder(" SELECT r.* FROM recipelog.recept r " +
                " join recept_categorie rc on (r.id = rc.recept_id) " +
                " where rc.categorie_id in (select c.id from categorie c where ");
        for (int i = 0; i < categories.size(); i++) {
            if (i == 0)
                s.append(" c.id = ").append(categories.get(i).getId().toString());
            else
                s.append(" or c.id = ").append(categories.get(i).getId().toString());
        }

        s.append(" ) group by r.id having count(distinct rc.categorie_id) = ")
                .append(categories.size())
                .append(" order by RAND() LIMIT ")
                .append(limit);
        Query query = em.createNativeQuery(s.toString(), Recept.class);

        return (List<Recept>) query.getResultList();
    }
}
