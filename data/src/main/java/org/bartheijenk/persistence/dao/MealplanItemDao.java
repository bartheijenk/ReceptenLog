package org.bartheijenk.persistence.dao;

import org.bartheijenk.persistence.entity.MealplanItem;
import org.bartheijenk.persistence.entity.Recept;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MealplanItemDao extends Dao<MealplanItem, Long> {

    public MealplanItem save(MealplanItem mealplanItem, Long receptId) {
        Recept recept = em.getReference(Recept.class, receptId);
        mealplanItem.setRecept(recept);
        super.save(mealplanItem);
        return mealplanItem;
    }

    public List<MealplanItem> findAllFromDate(LocalDate dateFrom) {
        TypedQuery<MealplanItem> query = em.createQuery("select m from MealplanItem m where m.date > :dateFrom", MealplanItem.class);
        query.setParameter("dateFrom", dateFrom);
        return query.getResultList();
    }
}
