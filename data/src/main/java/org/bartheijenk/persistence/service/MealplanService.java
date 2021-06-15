package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.MealplanItemDao;
import org.bartheijenk.persistence.entity.MealplanItem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MealplanService implements IMealplanService {

    @Inject
    private MealplanItemDao mealplanItemDao;

    @Override
    public MealplanItem addMealplanItem(MealplanItem mealplanItem) {
        return mealplanItemDao.save(mealplanItem, mealplanItem.getRecept().getId());
    }

    @Override
    public List<MealplanItem> getAll(LocalDate dateFrom) {
        return mealplanItemDao.findAllFromDate(dateFrom);
    }
}
