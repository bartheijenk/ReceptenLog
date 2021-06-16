package org.bartheijenk.persistence.service;

import org.bartheijenk.persistence.dao.MealplanItemDao;
import org.bartheijenk.persistence.entity.MealplanItem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.bartheijenk.persistence.util.RecordNotFoundUtil.recordNotFound;
import static org.bartheijenk.persistence.util.RecordNotFoundUtil.throwRecordNotFound;

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
        List<MealplanItem> allFromDate = mealplanItemDao.findAllFromDate(dateFrom);
        return allFromDate;
    }

    @Override
    public Optional<MealplanItem> getById(Long id) {
        return mealplanItemDao.find(id);
    }

    @Override
    public MealplanItem changeDateTo(Long id, LocalDate date) {
        MealplanItem item = getById(id).orElseThrow(() -> recordNotFound(id));
        item.setDate(date);
        mealplanItemDao.update(item);
        return item;
    }

    @Override
    public void deleteById(Long id) {
        getById(id).ifPresentOrElse(mealplanItemDao::remove, throwRecordNotFound(id));
    }
}
