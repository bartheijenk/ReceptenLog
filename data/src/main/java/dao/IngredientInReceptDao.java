package dao;

import entity.IngredientInRecept;
import entity.IngredientInReceptKey;
import util.Dao;

import javax.persistence.EntityManager;

public class IngredientInReceptDao extends Dao<IngredientInRecept, IngredientInReceptKey> {

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
