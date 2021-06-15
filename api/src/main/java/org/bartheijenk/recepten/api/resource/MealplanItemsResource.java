package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.MealplanItem;
import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.IMealplanService;
import org.bartheijenk.recepten.api.payload.MealplanRequest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.util.List;

@Path("/mealplan")
public class MealplanItemsResource implements JsonResource {

    @Inject
    private IMealplanService mealplanService;

    @GET
    public List<MealplanItem> getAll(@QueryParam("dateFrom") String dateFrom) {
        if (dateFrom == null) {
            return mealplanService.getAll(LocalDate.now().minusDays(10L));
        } else {
            LocalDate date = LocalDate.parse(dateFrom);
            return mealplanService.getAll(date);
        }
    }

    @POST
    public MealplanItem postItem(MealplanRequest item) {
        MealplanItem mealplanItem = MealplanItem.builder()
                .recept(Recept.builder().id(item.getRecept()).build())
                .date(LocalDate.parse(item.getDate()))
                .isAvondeten(item.getIsAvondeten())
                .servings(item.getServings())
                .build();
        return mealplanService.addMealplanItem(mealplanItem);
    }
}
