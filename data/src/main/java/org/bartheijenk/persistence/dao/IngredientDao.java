package org.bartheijenk.persistence.dao;

import lombok.extern.log4j.Log4j2;
import org.bartheijenk.persistence.entity.Ingredient;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Log4j2

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IngredientDao extends Dao<Ingredient, Long> {

    public IngredientDao() {
    }

    public Ingredient findByName(String naam) {
        TypedQuery<Ingredient> query = em.createQuery("select i from Ingredient i where i.naam=:naam", Ingredient.class);
        query.setParameter("naam", naam);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public void saveIfNotExists(Ingredient ingredient) {
        if (findByName(ingredient.getNaam()) == null) {
            save(ingredient);
        }
    }
}
