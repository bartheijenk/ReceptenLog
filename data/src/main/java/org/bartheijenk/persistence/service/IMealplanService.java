package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.entity.MealplanItem;

import java.time.LocalDate;
import java.util.List;

public interface IMealplanService {

    MealplanItem addMealplanItem(MealplanItem mealplanItem);

    List<MealplanItem> getAll(LocalDate minusDays);
}
