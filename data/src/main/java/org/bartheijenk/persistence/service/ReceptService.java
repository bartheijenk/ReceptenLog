package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.CategorieDao;
import org.bartheijenk.persistence.dao.IngredientDao;
import org.bartheijenk.persistence.dao.IngredientInReceptDao;
import org.bartheijenk.persistence.dao.ReceptDao;
import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Ingredient;
import org.bartheijenk.persistence.entity.IngredientInRecept;
import org.bartheijenk.persistence.entity.Recept;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

import static org.bartheijenk.persistence.util.RecordNotFoundUtil.throwRecordNotFound;

@ApplicationScoped
public class ReceptService implements IReceptService {

    @Inject
    private ReceptDao receptDao;
    @Inject
    private IngredientDao ingredientDao;
    @Inject
    private CategorieDao categorieDao;
    @Inject
    private IngredientInReceptDao ingredientInReceptDao;

    public Recept saveRecept(Recept recept) {
        mergeIngredienten(recept);
        mergeCategories(recept);

        receptDao.save(recept);

        return recept;
    }

    public List<Recept> getAllRecepten() {
        return receptDao.getReceptenNaamOpId();
    }

    @Deprecated
    public List<Recept> getReceptNamenEnIDPerCategorie(Categorie categorie) {
        return receptDao.getReceptenNaamOpIdPerCategorie(categorie);
    }

    @Override
    public List<Recept> getReceptenByQuery(List<Long> cats, List<Long> ingrs, List<String> brons,
                                           int minServings, int maxServings) {
        List<Recept> allRecepten = getAllRecepten();
        List<Categorie> categories = this.categorieDao.findAllById(cats);
        List<Ingredient> ingredients = this.ingredientDao.findAllById(ingrs);

        return allRecepten.stream()
                .filter(r -> categories == null || r.getCategories().containsAll(categories))
                .filter(r -> ingredients == null || r.getIngredienten().stream()
                        .filter(i -> ingredients.contains(i.getIngredient()))
                        .count() >= ingredients.size()
                )
                .filter(r -> brons.isEmpty() || brons.contains(r.getBron()))
                .filter(r -> minServings == 0 || minServings <= r.getServings())
                .filter(r -> maxServings == 0 || r.getServings() <= maxServings)
                .collect(Collectors.toList());
    }

    public Optional<Recept> getReceptById(Long id) {
        return receptDao.find(id);
    }

    public List<Recept> zoekReceptenOpTitel(String zoekTermen) {
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
