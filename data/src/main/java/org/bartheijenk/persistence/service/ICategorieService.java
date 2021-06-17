package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Recept;

import java.util.List;
import java.util.Optional;

public interface ICategorieService {

    /**
     * Gives a list of all tags
     *
     * @return a List of Tag objects
     */
    List<Categorie> getAllCategories();

    /**
     * Gives a Tag object with specified ID
     *
     * @param id the ID in Long
     * @return A Tag object or null
     */
    Optional<Categorie> getTagById(Long id);

    List<Recept> getReceptenOfCategorie(Categorie categorie);
}
