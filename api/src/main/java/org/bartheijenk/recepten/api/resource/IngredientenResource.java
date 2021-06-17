package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.Ingredient;
import org.bartheijenk.persistence.service.IIngredientenService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/ingredienten")
public class IngredientenResource implements JsonResource {

    @Inject
    private IIngredientenService ingredientenService;

    @GET
    public List<Ingredient> getAllIngredienten() {
        return this.ingredientenService.getAllIngredients();
    }
}
