package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.IReceptService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/recepten")
public class ReceptenResource implements JsonResource {

    @Inject
    private IReceptService receptService;

    @Inject
    private ReceptResource receptResource;

    @GET
    public List<Recept> get(@QueryParam("q") String q) {
        return q == null ?
                receptService.getAllRecepten() :
                receptService.zoekRecepten(q);
    }

    @POST
//    @Authorized
    public Recept post(Recept recept) {
        return receptService.saveRecept(recept);
    }

    @Path("{id}")
    public ReceptResource getById(@PathParam("id") Long id) {
        return this.receptResource.init(id);
    }
}
