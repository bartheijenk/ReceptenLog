package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.entity.Recept;

import java.util.List;

public interface IRandomizerService {

    /**
     * Returns a random map of recipes
     *
     * @param limit how many recipes should be gotten
     * @return a Map of Long Ids with String names of recipes
     */
    public List<Recept> getRandomizedList(int limit);

    /**
     * Returns a random map of recipes based on provided tags
     *
     * @param limit  How many recipes should be gotten
     * @param tagIds A List of Long Ids of tags
     * @return returns a Map of Long Ids and String names of recipes or null if no tags are found for the IDs
     */
    public List<Recept> getRandomizedListWithCategories(int limit, List<Long> tagIds);
}
