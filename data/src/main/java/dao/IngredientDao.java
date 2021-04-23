package dao;

import entity.Ingredient;
import util.Dao;

import javax.persistence.EntityManager;

public class IngredientDao extends Dao<Ingredient, Long> {

    private static IngredientDao instance;

    private IngredientDao(EntityManager em) {
        super(em);
    }

    public IngredientDao getInstance(EntityManager em) {
        if (instance == null) {
            instance = new IngredientDao(em);
        }
        return instance;
    }
}
