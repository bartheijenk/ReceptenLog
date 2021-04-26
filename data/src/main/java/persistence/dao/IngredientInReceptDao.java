package persistence.dao;

import persistence.entity.IngredientInRecept;
import persistence.util.Dao;

import javax.persistence.EntityManager;

public class IngredientInReceptDao extends Dao<IngredientInRecept, Long> {

    private static IngredientInReceptDao instance;

    private IngredientInReceptDao(EntityManager em) {
        super(em);
    }

    public IngredientInReceptDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new IngredientInReceptDao(em);
        }
        return instance;
    }
}
