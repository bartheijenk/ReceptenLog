package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.IReceptService;
import org.bartheijenk.persistence.util.RecordNotFoundException;
import org.bartheijenk.recepten.api.util.Response;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

public class ReceptResource implements JsonResource {

    @Inject
    private IReceptService receptService;

    private Long id;

    public ReceptResource init(Long id) {
        this.id = id;
        return this;
    }

    @GET
    public Recept get() {
        return receptService.getReceptById(id)
                .orElseThrow(() -> Response.badRequest(id));
    }

    @DELETE
    public void delete() {
        try {
            receptService.deleteRecept(id);
        } catch (RecordNotFoundException e) {
            throw Response.badRequest(id);
        }
    }

    @PUT
    public Recept update(Recept recept) {
        return receptService.updateRecept(id, recept)
                .orElseThrow(() -> Response.badRequest(id));
    }
}
