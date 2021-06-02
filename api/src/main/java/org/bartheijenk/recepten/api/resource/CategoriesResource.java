package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.service.ICategorieService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/categorie")
public class CategoriesResource implements JsonResource {

    @Inject
    private ICategorieService categorieService;

    @Inject
    private CategorieResource categorieResource;

    @GET
    public List<Categorie> getCategorieen() {
        return categorieService.getAllCategories();
    }

    @Path("{id}")
    public CategorieResource getById(@PathParam("id") Long id) {
        return this.categorieResource.init(id);
    }
}
