package persistence.dao;

import lombok.extern.log4j.Log4j2;
import persistence.entity.Ingredient;
import persistence.util.Dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Log4j2
public class IngredientDao extends Dao<Ingredient, Long> {

    private static IngredientDao instance;

    private IngredientDao(EntityManager em) {
        super(em);
    }

    public static IngredientDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new IngredientDao(em);
        }
        return instance;
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

    @SuppressWarnings("unchecked")
    public List<Ingredient> findAllByName(List<String> list) {
        Query query = em.createQuery("select i from Ingredient i where i.naam in :list");
        query.setParameter("list", list);
        return query.getResultList();
    }

    public void saveIfNotExists(Ingredient ingredient) {
        if (findByName(ingredient.getNaam()) == null) {
            save(ingredient);
        }
    }
}
