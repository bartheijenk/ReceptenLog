package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.Categorie;
import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.ICategorieService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

import static org.bartheijenk.recepten.api.util.Response.badRequest;

public class CategorieResource implements JsonResource {

    @Inject
    private ICategorieService categorieService;

    private Long id;

    public CategorieResource init(Long id) {
        this.id = id;
        return this;
    }


    @GET
    public Categorie getCategorieById() {
        return categorieService.getTagById(id)
                .orElseThrow(() -> badRequest(id));
    }

    @GET
    @Path("/recept")
    public List<Recept> getReceptenOfCategorie() {
        return categorieService.getReceptenOfCategorie(getCategorieById());
    }
}
