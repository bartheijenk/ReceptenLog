package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.ReceptService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/recept")
public class ReceptResource implements JsonResource {

    @Inject
    private ReceptService receptService;

    @GET
    public List<Recept> get(@QueryParam("q") String q) {
        return q == null ?
                receptService.getAllReceptNamenEnID() :
                receptService.zoekRecepten(q);
    }

    @GET
    @Path("{id}")
    public Recept get(@PathParam("id") Long id) {
        return receptService.getReceptById(id);
    }

    @POST
    public Recept post(Recept recept) {
        return receptService.saveRecept(recept);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        receptService.deleteRecept(id);
    }

    @PUT
    @Path("{id}")
    public Recept update(@PathParam("id") Long id, Recept recept) {
        return receptService.updateRecept(id, recept);
    }
}
