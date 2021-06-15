package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.entity.MealplanItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IMealplanService {

    MealplanItem addMealplanItem(MealplanItem mealplanItem);

    List<MealplanItem> getAll(LocalDate minusDays);

    Optional<MealplanItem> getById(Long id);

    MealplanItem changeDateTo(Long id, LocalDate date);

    void deleteById(Long id);
}
