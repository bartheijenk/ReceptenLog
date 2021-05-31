package org.bartheijenk.recepten.api;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.ReceptService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-world")
public class HelloResource {

    @Inject
    private Logger log;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Recept hello() {
        log.debug("Getting resource");
        return ReceptService.getInstance().getReceptById(2L);
//        return CategorieService.getInstance().getAllCategories();
    }


}