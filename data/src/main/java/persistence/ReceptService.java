package persistence;

import persistence.dao.IngredientDao;
import persistence.dao.ReceptDao;
import persistence.dao.TagDao;
import persistence.entity.Ingredient;
import persistence.entity.IngredientInRecept;
import persistence.entity.Recept;
import persistence.entity.Tag;

import javax.persistence.EntityManager;
import java.util.*;

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
    private final TagDao tagDao = TagDao.getInstance(EntityManagerProvider.getEntityManager());

    public ReceptService(EntityManager em) {
        receptDao = ReceptDao.getInstance(em);
        ingredientDao = IngredientDao.getInstance(em);
    }

    public ReceptService() {
    }

    /**
     * Saves the recipe into the database
     *
     * @param recept The to save recipe
     * @return returns the saved recipe
     */
    public Recept saveRecept(Recept recept) {
        mergeIngredienten(recept);
        mergeTags(recept);

        receptDao.save(recept);

        return recept;
    }


    /**
     * Gives a map of all recipes names with their respective IDs
     *
     * @return a map of Long Ids with String names
     */
    public Map<Long, String> getAllReceptNamenEnID() {
        return receptDao.getReceptenNaamOpId();
    }

    /**
     * Gives a map of all Recipe names with their respective IDs per Tag provided
     *
     * @param tag the to be provided tag
     * @return a map of Long IDs with String names
     */
    public Map<Long, String> getReceptNamenEnIDPerTag(Tag tag) {
        return receptDao.getReceptenNaamOpIdPerTag(tag);
    }

    /**
     * Gets a recipe by its ID
     *
     * @param id the ID in Long
     * @return the found recipe, returns null if nothing found
     */
    public Recept getReceptById(Long id) {
        return receptDao.find(id);
    }
    //saves Tags if necessary then grabs them from the database to fill IDs


    /**
     * Gets a recipe by searching for specific terms
     *
     * @param zoekTermen search terms provided by the user
     * @return a List of Recept results. Is an Empty list if nothing found
     */
    public List<Recept> zoekRecepten(String zoekTermen) {
        List<Recept> results = new LinkedList<>();
        List<Recept> recepten = receptDao.findAll();
        String finalZoekTermen = zoekTermen.toLowerCase(Locale.ROOT);

        //Eerst zoeken op de volledige zin
        recepten.stream()
                .filter(recept -> recept.getTitel().toLowerCase(Locale.ROOT).contains(finalZoekTermen))
                .forEach(results::add);

        //Dan zoeken op elk woord apart
        String[] losseTermen = zoekTermen.split(" ");
        for (String s : losseTermen) {
            recepten.stream()
                    .filter(recept -> recept.getTitel().toLowerCase(Locale.ROOT).contains(s))
                    .forEach(recept -> {
                        if (!results.contains(recept)) {
                            results.add(recept);
                        }
                    });
        }
        return results;
    }

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
