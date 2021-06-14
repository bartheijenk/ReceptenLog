package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.IngredientDao;
import org.bartheijenk.persistence.entity.Ingredient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class IngredientenService implements IIngredientenService {

    @Inject
    private IngredientDao ingredientDao;

    @Override
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = ingredientDao.findAll();
        ingredients.sort(Comparator.comparing(Ingredient::getNaam));
        return ingredients;
    }
}
