package persistence;

import persistence.dao.IngredientDao;
import persistence.dao.ReceptDao;
import persistence.entity.Ingredient;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;

import javax.persistence.EntityManager;
import java.util.Set;

public class ReceptInvoerenService {

    private static ReceptInvoerenService instance;

    public static ReceptInvoerenService getInstance() {
        if (instance == null) {
            instance = new ReceptInvoerenService();
        }
        return instance;
    }

    private ReceptDao receptDao = ReceptDao.getInstance(EntityManagerProvider.getEntityManager());
    private IngredientDao ingredientDao = IngredientDao.getInstance(EntityManagerProvider.getEntityManager());

    public ReceptInvoerenService(EntityManager em) {
        receptDao = ReceptDao.getInstance(em);
        ingredientDao = IngredientDao.getInstance(em);
    }

    public ReceptInvoerenService() {
    }

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
