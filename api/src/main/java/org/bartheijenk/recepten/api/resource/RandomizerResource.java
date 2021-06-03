package org.bartheijenk.recepten.api.resource;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.IRandomizerService;
import org.bartheijenk.persistence.service.IReceptService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/randomizer")
public class RandomizerResource implements JsonResource {

    @Inject
    private Logger log;

    @Inject
    private IRandomizerService randomizerService;

    @Inject
    private IReceptService receptService;

    @GET
    public List<Recept> getRandomizedList(@QueryParam("limit") int limit,
                                          @QueryParam("catIds") List<Long> catIds) {
        if (catIds == null || catIds.isEmpty()) {
            return randomizerService.getRandomizedList(limit);
        } else {
            return randomizerService.getRandomizedListWithCategories(limit, catIds);
        }
    }
}
