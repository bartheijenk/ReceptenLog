package persistence;

import persistence.dao.IngredientDao;
import persistence.dao.ReceptDao;
import persistence.dao.TagDao;
import persistence.entity.Ingredient;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;
import persistence.entity.Tag;

import javax.persistence.EntityManager;
import java.util.HashSet;
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
    private TagDao tagDao = TagDao.getInstance(EntityManagerProvider.getEntityManager());

    public ReceptInvoerenService(EntityManager em) {
        receptDao = ReceptDao.getInstance(em);
        ingredientDao = IngredientDao.getInstance(em);
    }

    public ReceptInvoerenService() {
    }

    public Recept saveRecept(Recept recept) {
        mergeIngredienten(recept);
        mergeTags(recept);

        receptDao.save(recept);

        return recept;
    }

    private void mergeTags(Recept recept) {
        Set<Tag> tags = new HashSet<>();
        for (Tag tag : recept.getTags()) {
            tagDao.saveIfNotExists(tag);
            tags.add(tagDao.findByNaam(tag.getNaam()));
        }
        recept.setTags(tags);
    }

    private void mergeIngredienten(Recept recept) {
        Set<IngredientInRecept> ingredienten = recept.getIngredienten();

        for (IngredientInRecept ingredientInRecept : ingredienten) {
            ingredientDao.saveIfNotExists(ingredientInRecept.getIngredient());
            Ingredient byName = ingredientDao.findByName(ingredientInRecept.getIngredient().getNaam());
            ingredientInRecept.setIngredient(byName);
        }

        recept.setIngredienten(ingredienten);
    }
}
