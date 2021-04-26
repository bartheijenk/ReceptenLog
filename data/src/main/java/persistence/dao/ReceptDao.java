package persistence.dao;

import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;
import persistence.util.Dao;

import javax.persistence.EntityManager;

public class ReceptDao extends Dao<Recept, Long> {

    private static ReceptDao instance;

    private ReceptDao(EntityManager em) {
        super(em);
    }

    public static ReceptDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new ReceptDao(em);
        }
        return instance;
    }

    public void saveIngredientInRecept(IngredientInRecept ingredientInRecept) {
        try {
            em.getTransaction().begin();
//            ingredientInRecept.setId(new IngredientInReceptKey(ingredientInRecept.getRecept().getId(),
//                    ingredientInRecept.getIngredient().getId()));
            em.persist(ingredientInRecept);
            em.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
