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
import java.util.Map;
import java.util.Set;

public class ReceptService {

    private static ReceptService instance;

    public static ReceptService getInstance() {
        if (instance == null) {
            instance = new ReceptService();
        }
        return instance;
    }

    private ReceptDao receptDao = ReceptDao.getInstance(EntityManagerProvider.getEntityManager());
    private IngredientDao ingredientDao = IngredientDao.getInstance(EntityManagerProvider.getEntityManager());
    private TagDao tagDao = TagDao.getInstance(EntityManagerProvider.getEntityManager());

    public ReceptService(EntityManager em) {
        receptDao = ReceptDao.getInstance(em);
        ingredientDao = IngredientDao.getInstance(em);
    }

    public ReceptService() {
    }

    public Recept saveRecept(Recept recept) {
        mergeIngredienten(recept);
        mergeTags(recept);

        receptDao.save(recept);

        return recept;
    }

    public Map<Long, String> getAllReceptNamenEnID() {
        return receptDao.getReceptenNaamOpId();
    }

    public Recept getReceptById(Long id) {
        return receptDao.find(id);
    }

    //saves Tags if necessary then grabs them from the database to fill IDs
    private void mergeTags(Recept recept) {
        Set<Tag> tags = new HashSet<>();
        for (Tag tag : recept.getTags()) {
            tagDao.saveIfNotExists(tag);
            tags.add(tagDao.findByNaam(tag.getNaam()));
        }
        recept.setTags(tags);
    }

    //saves ingredients if necessary then grabs them from the database to fill IDs
    private void mergeIngredienten(Recept recept) {
        Set<IngredientInRecept> ingredienten = recept.getIngredienten();

        for (IngredientInRecept ingredientInRecept : ingredienten) {
            Ingredient ingredient = ingredientInRecept.getIngredient();

            ingredientDao.saveIfNotExists(ingredient);
            Ingredient byName = ingredientDao.findByName(ingredient.getNaam());
            ingredientInRecept.setIngredient(byName);
        }

        recept.setIngredienten(ingredienten);
    }
}