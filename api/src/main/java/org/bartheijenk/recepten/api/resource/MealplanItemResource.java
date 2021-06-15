package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.MealplanItem;
import org.bartheijenk.persistence.service.IMealplanService;
import org.bartheijenk.persistence.util.RecordNotFoundException;
import org.bartheijenk.recepten.api.util.Response;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

import static org.bartheijenk.recepten.api.util.Response.badRequest;


public class MealplanItemResource implements JsonResource {

    @Inject
    private IMealplanService mealplanService;
    private Long id;

    public MealplanItemResource init(Long id) {
        this.id = id;
        return this;
    }

    @GET
    public MealplanItem getById() {
        return mealplanService.getById(id).orElseThrow(() -> badRequest(id));
    }

    @PUT
    public MealplanItem changeDate(MealplanItem item) {
        try {

            return mealplanService.changeDateTo(id, item.getDate());
        } catch (RecordNotFoundException e) {
            throw Response.badRequest(id);
        }
    }

    @DELETE
    public void deleteById() {
        try {
            mealplanService.deleteById(id);
        } catch (RecordNotFoundException e) {
            throw Response.badRequest(id);
        }
    }
}
