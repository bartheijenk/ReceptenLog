package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.CategorieDao;
import org.bartheijenk.persistence.dao.IngredientDao;
import org.bartheijenk.persistence.dao.ReceptDao;
import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Ingredient;
import org.bartheijenk.persistence.entity.IngredientInRecept;
import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import java.util.*;

public class ReceptService {

    private static ReceptService instance;

    public ReceptService() {
        EntityManager em = EntityManagerProvider.getEntityManager();
        receptDao = ReceptDao.getInstance(em);
        ingredientDao = IngredientDao.getInstance(em);
        categorieDao = CategorieDao.getInstance(em);
    }

    public static ReceptService getInstance() {
        if (instance == null) {
            instance = new ReceptService(EntityManagerProvider.getEntityManager());
        }
        return instance;
    }

    private final ReceptDao receptDao;
    private final IngredientDao ingredientDao;
    private final CategorieDao categorieDao;

    public ReceptService(EntityManager em) {
        receptDao = ReceptDao.getInstance(em);
        ingredientDao = IngredientDao.getInstance(em);
        categorieDao = CategorieDao.getInstance(em);
    }

    /**
     * Saves the recipe into the database
     *
     * @param recept The to save recipe
     * @return returns the saved recipe
     */
    public Recept saveRecept(Recept recept) {
        mergeIngredienten(recept);
        mergeCategories(recept);

        receptDao.save(recept);

        return recept;
    }


    /**
     * Gives a map of all recipes names with their respective IDs
     *
     * @return a List of Recipes
     */
    public List<Recept> getAllReceptNamenEnID() {
        return receptDao.getReceptenNaamOpId();
    }

    /**
     * Gives a map of all Recipe names with their respective IDs per Tag provided
     *
     * @param categorie the to be provided tag
     * @return a set of Recipes
     */
    public List<Recept> getReceptNamenEnIDPerCategorie(Categorie categorie) {
        return receptDao.getReceptenNaamOpIdPerCategorie(categorie);
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


    /**
     * Gets a recipe by searching for specific terms
     *
     * @param zoekTermen search terms provided by the user
     * @return a List of Recept results. Is an Empty list if nothing found
     */
    public List<Recept> zoekRecepten(String zoekTermen) {
        Set<Recept> results = new LinkedHashSet<>();
        List<Recept> recepten = receptDao.findAll();
        String finalZoekTermen = zoekTermen.toLowerCase(Locale.ROOT);

        //Eerst zoeken op de volledige zin
        results.addAll(zoekOpVolledigeTermenInTitel(finalZoekTermen, recepten));

        //Dan zoeken op elk woord apart
        results.addAll(zoekOpLosseTermenInTitel(zoekTermen, recepten));

        return List.copyOf(results);
    }

    /**
     * Deletes Recipe by id
     *
     * @param id the ID in Long
     */
    public void deleteRecept(Long id) {
        receptDao.remove(getReceptById(id));
    }

    private List<Recept> zoekOpLosseTermenInTitel(String zoekTermen, List<Recept> recepten) {
        List<Recept> results = new LinkedList<>();
        String[] losseTermen = zoekTermen.split(" ");
        for (String s : losseTermen) {
            recepten.stream()
                    .filter(recept -> recept.getTitel().toLowerCase(Locale.ROOT).contains(s))
                    .forEach(results::add);
        }
        return results;
    }

    private List<Recept> zoekOpVolledigeTermenInTitel(String finalZoekTermen, List<Recept> recepten) {
        List<Recept> results = new LinkedList<>();
        recepten.stream()
                .filter(recept -> recept.getTitel().toLowerCase(Locale.ROOT).contains(finalZoekTermen))
                .forEach(results::add);
        return results;
    }

    private void mergeCategories(Recept recept) {
        Set<Categorie> categories = new HashSet<>();
        for (Categorie categorie : recept.getCategories()) {
            categorieDao.saveIfNotExists(categorie);
            categories.add(categorieDao.findByNaam(categorie.getNaam()));
        }
        recept.setCategories(categories);
    }

    private void mergeIngredienten(Recept recept) {
        Set<IngredientInRecept> ingredienten = recept.getIngredienten();

        for (IngredientInRecept ingredientInRecept : ingredienten) {
            Ingredient ingredient = ingredientInRecept.getIngredient();

            ingredientDao.saveIfNotExists(ingredient);
            Ingredient byName = ingredientDao.findByName(ingredient.getNaam());
            ingredientInRecept.setIngredient(byName);
            ingredientInRecept.setRecept(recept);
        }
        recept.setIngredienten(ingredienten);
    }

    public Recept updateRecept(Long id, Recept recept) {
        //TODO too long didn't program
        return null;
    }
}
