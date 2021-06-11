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
    public List<Recept> get(
            @QueryParam("q") String q,
            @QueryParam("filter") Boolean filter,
            @QueryParam("cats") List<Long> cats,
            @QueryParam("ingr") List<Long> ingr,
            @QueryParam("bron") List<String> bron,
            @QueryParam("minSer") int minSer,
            @QueryParam("maxSer") int maxSer
    ) {
        if (filter != null) {
            return receptService.getReceptenByQuery(q, cats, ingr, bron, minSer, maxSer);
        } else {
            return q == null ?
                    receptService.getAllRecepten() :
                    receptService.zoekReceptenOpTitel(q);
        }
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
