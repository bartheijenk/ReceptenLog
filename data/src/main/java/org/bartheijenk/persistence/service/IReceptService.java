package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Recept;

import java.util.List;
import java.util.Optional;

public interface IReceptService {

    /**
     * Gives a map of all recipes names with their respective IDs
     *
     * @return a List of Recipes
     */
    List<Recept> getAllRecepten();

    /**
     * Gets a recipe by searching for specific terms
     *
     * @param q search terms provided by the user
     * @return a List of Recept results. Is an Empty list if nothing found
     */
    List<Recept> zoekReceptenOpTitel(String q);

    /**
     * Gets a recipe by its ID
     *
     * @param id the ID in Long
     * @return an Optional of the Recipe
     */
    Optional<Recept> getReceptById(Long id);

    /**
     * Saves the recipe into the database
     *
     * @param recept The to save recipe
     * @return returns the saved recipe
     */
    Recept saveRecept(Recept recept);

    /**
     * Deletes Recipe by id
     *
     * @param id the ID in Long
     */
    void deleteRecept(Long id);

    /**
     * Updates Recipe
     *
     * @param id     The ID of the Recepit
     * @param recept The changes that need to be made
     * @return an Optional of the Recipe
     */
    Optional<Recept> updateRecept(Long id, Recept recept);

    /**
     * Gives a map of all Recipe names with their respective IDs per Tag provided
     *
     * @param categorie the to be provided tag
     * @return a List of Recipes
     */
    List<Recept> getReceptNamenEnIDPerCategorie(Categorie categorie);


    List<Recept> getReceptenByQuery(List<Long> cats, List<Long> ingrs, List<String> brons,
                                    int minServings, int maxServings);
}
