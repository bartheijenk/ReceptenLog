package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.CategorieDao;
import org.bartheijenk.persistence.dao.IngredientDao;
import org.bartheijenk.persistence.dao.ReceptDao;
import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Ingredient;
import org.bartheijenk.persistence.entity.IngredientInRecept;
import org.bartheijenk.persistence.entity.Recept;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

import static org.bartheijenk.persistence.util.RecordNotFoundUtil.throwRecordNotFound;

@ApplicationScoped
public class ReceptService implements IReceptService {

    @Inject
    private ReceptDao receptDao;
    @Inject
    private IngredientDao ingredientDao;
    @Inject
    private CategorieDao categorieDao;

    public Recept saveRecept(Recept recept) {
        mergeIngredienten(recept);
        mergeCategories(recept);

        receptDao.save(recept);

        return recept;
    }

    public List<Recept> getAllReceptNamenEnID() {
        return receptDao.getReceptenNaamOpId();
    }

    @Deprecated
    public List<Recept> getReceptNamenEnIDPerCategorie(Categorie categorie) {
        return receptDao.getReceptenNaamOpIdPerCategorie(categorie);
    }

    public Optional<Recept> getReceptById(Long id) {
        return receptDao.find(id);
    }

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

    public void deleteRecept(Long id) {
        getReceptById(id).ifPresentOrElse(receptDao::remove, throwRecordNotFound(id));
    }

    public Optional<Recept> updateRecept(Long id, Recept recept) {
        Optional<Recept> oldOpt = getReceptById(id);
        if (oldOpt.isPresent()) {
            recept.setId(id);
            receptDao.save(recept);
            return getReceptById(id);
        } else {
            return oldOpt;
        }
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
}
