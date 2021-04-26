package persistence;

import persistence.dao.IngredientDao;
import persistence.dao.ReceptDao;
import persistence.entity.Ingredient;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;

import java.util.Set;

public class ReceptInvoerenService {
    private final ReceptDao receptDao = ReceptDao.getInstance(EntityManagerProvider.getEntityManager());
    private final IngredientDao ingredientDao = IngredientDao.getInstance(EntityManagerProvider.getEntityManager());

    public Recept saveRecept(Recept recept) {
        Set<IngredientInRecept> ingredienten = recept.getIngredienten();

        for (IngredientInRecept ingredientInRecept : ingredienten) {
            ingredientDao.saveIfNotExists(ingredientInRecept.getIngredient());
            Ingredient byName = ingredientDao.findByName(ingredientInRecept.getIngredient().getNaam());
            ingredientInRecept.setIngredient(byName);
        }

        recept.setIngredienten(ingredienten);

        receptDao.save(recept);

        return receptDao.find(recept.getId());
    }
}
