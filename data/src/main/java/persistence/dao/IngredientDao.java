package persistence.dao;

import persistence.entity.Ingredient;
import persistence.util.Dao;

import javax.persistence.EntityManager;

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
}
